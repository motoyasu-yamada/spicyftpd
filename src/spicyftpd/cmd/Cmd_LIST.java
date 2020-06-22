package spicyftpd.cmd;

import java.io.File;
import java.io.OutputStream;
import java.net.Socket;
import spicyftpd.FtpCmdException;
import spicyftpd.FtpThread;
import spicyftpd.filesystem.FileSystem;
import spicyftpd.filesystem.FileSystemFactory;

/**
*   Ftpコマンドを実装するクラス
*
*   行形式:
*       モード リンク 所有者 グループ サイズ 日付時刻 名前<CRLF>
*       ※  各フィールドは空白スペース1 個で区切る
*   フィールド説明:
*       モード:
*           モードは10桁
*           1桁目の説明:
*               ファイル形式
*               d  ディレクトリーを表します。  
*               b  ブロック特殊ファイルを表します。  
*               c  文字特殊ファイルを表します。  
*               l  シンボリック・リンクを表します。-N フラグが入力されたか、シンボリック・リンクのリンク先ファイルがありませんでした。  
*               p  先入れ先出し (FIFO) 特殊ファイルを表します。  
*               s  ローカル・ソケットを表します。  
*               -  普通のファイルを表します。  
*           2桁目から10桁目までの9桁の説明:
*               所有者、グループ、その他のユーザの許可情報
*               それぞれ3桁であらわす
*               r  read (読み取り)  
*               w  書き込み (編集)  
*               x  実行 (検索)  
*               -  該当する許可情報の否認
*       リンク:
*           ファイルへのリンクの数
*           右詰めで5文字に満たない部分がある場合はブランクで埋める
*       所有者:
*           ファイルの所有者
*           文字は左詰めで10文字に満たない桁にはブランクを埋める
*       グループ:
*           ファイルの所有グループ
*           文字は左詰めで10文字に満たない桁にはブランクを埋める
*       サイズ:
*           ファイルのサイズ
*           右詰めで10文字に満たない部分がある場合はブランクを埋める
*       日付時刻:
*           ファイルの変更時刻
*           左詰めで12文字に満たない桁にはブランクを埋める
*           変更が過去 180 日以内に行われたものである場合 
*               Mmm dd hh:mm
*           変更が過去 180 日以降に行われたものである場合、
*               Mmm dd yyyy
*           各フィールドの意味
*               Mmm  月名の略。  
*               dd  日にち  右詰めで2文字に満たない場合にはブランクで埋める
*               hh  時間    00 から 23 までの 2 桁数字
*                           右詰めで2文字に満たない場合にはゼロで埋める
*               mm  分      00 から 59 までの 2 桁数字
*                           右詰めで2文字に満たない場合にはゼロで埋める
*               yyyy  年    4 桁数字
*       名前:
*           オブジェクトの可変長の名前
*           -CRLF(復帰 (CR) と改行 (LF) のペア) が最後尾に付けられる。
*           名前にはブランクを含めることができます。 
*   
*   例:
*       drwxrwxrwx   4 QSYS           0    51200 Feb  9 21:28 home
*/
public class Cmd_LIST implements Cmd {

    /**
    *   @param  args    コマンドの引数
    *   @param  thread  コマンドを実行するFtpスレッド
    */
    public void cmd(String[] args,FtpThread thread) throws Exception {
        //  ログオンしていないのにこのコマンドは実行できない
        thread.checkLogon();

        File directory;
        switch(args.length) {
        case 0:
            directory = thread.currentDirectory();
            break;
        case 1:
            directory = thread.file(args[0]);
            break;
        default:
            throw new FtpCmdException("501 Syntax error in parameters or arguments");
        }

        FileSystem system = FileSystemFactory.getInstance();
        StringBuffer data = new StringBuffer();
        final File[] list = directory.listFiles();
        if (list != null) {
            final int count = list.length;
            for(int n = 0;n < count;n++) {
                File file = list[n];
                //  モード
                data.append(getFileType(file));
                data.append(system.getOwnerPermission(file));
                data.append(system.getGroupPermission(file));
                data.append(system.getAllPermission(file)  );
                data.append(' ');
                //  リンク
                formatRight(data,system.getLink(file),5,' ');
                data.append(' ');

                //  所有者
                formatLeft(data,system.getOwner(file),10);
                data.append(' ');

                //  グループ
                formatLeft(data,system.getGroup(file),10);
                data.append(' ');

                //  サイズ
                formatRight(data,(int)file.length(),10,' ');
                data.append(' ');

                //  日付
                data.append(getLastModified(file.lastModified()));
                data.append(' ');

                //  ファイル名
                data.append(file.getName());
                //  改行コード
                data.append("\r\n");
            }
        }

        thread.output("150 ASCII data"); 

        System.out.println(data.toString());
        Socket transfer = thread.transferSocket();
        OutputStream output = transfer.getOutputStream();
        output.write(data.toString().getBytes());
        output.close();
        transfer.close();

        thread.output("226 transfer complete"); 
    }

    /**
    *   ファイル形式を取得する
    *   @return 
    *       d  ディレクトリー
    *       -  普通のファイル
    *   実装上返さない値:
    *       b  ブロック特殊ファイル
    *       c  文字特殊ファイル  
    *       l   シンボリック・リンク -N フラグが入力されたか、
    *           シンボリック・リンクのリンク先ファイルがない
    *       p  先入れ先出し (FIFO) 特殊ファイル  
    *       s  ローカル・ソケット  
    */
    private final char getFileType(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        if(file.isDirectory()) {
            return 'd';
        }
        return '-';
    }

    /**
    *   日付をフォーマットする
    *   @param  long
    */
    private static final String getLastModified(long time) {
        return "Feb  9 21:28";
    }

    /**
    *   文字列を'空白'で左詰にして長さをlengthに揃える。
    *
    *   *   src文字列の長さが length より短い場合は、
    *       文字列の左に'空白'をつめて左詰にして
    *       文字列の長さを length に揃える。
    *   *   src文字列の長さが length より長い場合は、
    *       左からlength以上の文字列を削除する
    *
    *   @param  format  揃えた結果を追加する文字列
    *   @param  src 揃える元の文字列
    *   @param  length  文字列長
    */
    private static final void formatLeft(StringBuffer format,String src,int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length (" + length + ") <= 0 ");
        }
        if (src == null) {
            throw new NullPointerException();
        }

        int pad = length - src.length();
        if (pad == 0) {
        } else if (pad < 0) {
            src.substring(0,length);
        } else {
            assert(0 < pad);
            format.append(src);
            repeat(format,pad,' ');
        }
    }

    /**
    *   数値を文字列で表し
    *   文字列を'空白'で右詰にして長さをlengthに揃える。
    *
    *   *   数値をあらわす文字列の長さが length より短い場合は、
    *       文字列の左に'空白'をつめて右詰にして
    *       文字列の長さを length に揃える
    *   *    数値をあらわす文字列の長さが length より長い場合は、
    *       右からlength以上の文字列を削除する
    *
    *   @param  format  揃えた結果を追加する文字列
    *   @param  src     揃える元の文字列
    *   @param  length  文字列長
    *   @param  padchar 揃えるために詰めるための文字
    */
    private static final void formatRight(StringBuffer format,int number,int length,char padchar) {
        if (length <= 0) {
            throw new IllegalArgumentException("length (" + length + ") <= 0 ");
        }
        String src = String.valueOf(number);
        int pad = length - src.length();
        if (pad == 0) {
        } else if (pad < 0) {
            src.substring(-pad,src.length());
        } else {
            assert(0 < pad);
            repeat(format,pad,padchar);
            format.append(src);
        }
    }

    /**
    *   指定した文字を、指定した個数だけ繰り返して、文字列の後ろに追加する。
    *   @param  format  この文字列の後ろに追加する
    *   @param  count   繰り返す数      0より大きい場合
    *                                       指定した個数だけ文字を繰り返す
    *                                   0の場合
    *                                       なにもしない
    *                                   0より小さい場合
    *                                       IllegalArgumentException
    *   @param  c       繰り返す文字列
    */
    private static final void repeat(StringBuffer format,int count,char c) {
        if (format == null) {
            throw new NullPointerException();
        }
        if (count <= 0) {
            throw new IllegalArgumentException("count(" + count + ") < 0");
        }
        while (0 < count--) {
            format.append(c);
        }
    }
}
