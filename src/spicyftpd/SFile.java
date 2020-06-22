package spicyftpd;

import java.io.RandomAccessFile;

/**
*   CHROOTのエミュレーションクラス
*
*   @see    Chroot
*   @author m.yamada
*/
public final class ChrootFile {
    private Chroot _root;

    /**
    *   コンストラクタ
    *   直接インスタンスを生成することはできない。
    *   Chrootクラスによって生成される。
    */
    protected ChrootFile(Chroot root) {
        if (root == null) {
            throw new NullPointerException();
        }
        _root = root;
    }

    public final String getName() {
    }

    /**
    *   chrootされたルートディレクトリからのパスを取得する
    *   @return 絶対パス
    */
    public final String getPath() {
        return null;
    }

    /**
    *   このパスの親ディレクトリのパスを取得する。
    *   ルートディレクトリの親ディレクトリを取得しようとした場合
    *   ルートディレクトリのパスを返す。
    *   @return  パス
    */
    public final String getParent() {
    }

    /**
    *   ファイルへのランダムアクセスオブジェクトを取得する
    *   @param  access  RandomAccessFileコンストラクタに渡す
    *                   ファイルへのアクセス方法記述。
    */
    public final RandomAccessFile file(String access) {
    }
}

/**
*   chrootセキュリティに違反した場合の例外
*/
public class ChrootException extends Exception {
}

/**
*   CHROOTのエミュレーションクラス
*/
public final class Chroot {
    /**
    *   Chrootしたルートディレクトリ
    */
    private final File _rootPath;

    /**
    *   コンストラクタ
    *   @param  rootPath    このパスをルートにCHROOTのエミュレーションを行う。
    *   @exception  NullPointerException        引数rootPathがnull
    *               IllegalArgumentException    引数rootPathを指し示すパスが存在しない
    *                                           引数rootPathが指し示すパスがディレクトリではない
    */
    public Chroot(String rootPath) {
        if (rootPath == null) {
            throw new NullPointerException();
        }
        _rootPath = new File(rootPath);
        if (!_rootPath.exists()) {
            throw new IllegalArgumentException();
        }
        if (!_rootPath.isDirectory()) {
            throw new IllegalArgumentException();
        }
    }

    /**
    *   ファイルオブジェクトを取得する
    *
    *   @param  path    CHROOTをルートにした相対パス/絶対パス
    *                   パス区切りは Win/Unix共に常に'/'です。
    */
    public final ChrootFile file(String path) {
        if (path.startsWith('/')) {
        }
        int start = 0;
        int split = path.indexOf('/',start);
        String path1 = 
    }

    /**
    *   ファイルパスを結合させる
    *
    *   相対アクセス('..')によりルートディレクトリ以下にアクセスしようと
    *   した場合上位へのアクセスは無視する。
    *
    *   @param  path    CHROOTをルートにした相対パス/絶対パス
    */
    public final ChrootFile concat(ChrootFile file,String path) {
    }

    /**
    *   ファイルへのアクセス権限をチェックする
    *   @param  file    このファイルがルートディレクトリから
    *                   アクセス可能なパスにあるかどうか
    *   
    */
    private final void check(File file) throws ChrootException {
    }
}
