package spicyftpd.filesystem;

public class FileSystemFactory {

    /**
    *   現在のシステム環境に応じた、
    *   ファイルシステムオブジェクトを作成する。
    *   @return 現在のシステム環境に応じたファイルシステムオブジェクト
    */
    public static final FileSystem getInstance() {
        return new WinFileSystem();
    }

}