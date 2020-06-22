package spicyftpd.filesystem;

/**
*   ファイルシステムに依存しないパーミッション情報
*/
public final class Permission {
    /// 読み取り許可情報
    private final boolean _readable;
    /// 書き込み許可情報
    private final boolean _writable;
    /// 実行許可情報
    private final boolean _executable;

    /**
    *   コンストラクタ
    *   @param  r   読み取り許可情報
    *   @param  w   書き込み許可情報
    *   @param  e   実行許可情報
    */
    protected Permission(boolean r,boolean w,boolean e) {
        _readable   = r;
        _writable   = w;
        _executable = e;
    }

    /**
    *   読み取り許可情報
    *   @return true    -   許可
    *           false   -   不許可
    */
    public final boolean readable() {
        return _readable;
    }

    /**
    *   書き込み許可情報
    *   @return true    -   許可
    *           false   -   不許可
    */
    public final boolean writable() {
        return _writable;
    }

    /**
    *   実行許可情報
    *   @return true    -   許可
    *           false   -   不許可
    */
    public final boolean executable() {
        return _executable;
    }

    /**
    *   パーミッションを、リスト表示(ls)等で使われているような
    *   標準的名フォーマットで表示する。
    *   例:
    *       rwx
    *       r-x
    *       ---
    *   フィールドの意味:
    *       r  read (読み取り)  
    *       w  書き込み (編集)  
    *       x  実行 (検索)  
    *       -  該当する許可情報の否認
    */
    public final String toString() {
        StringBuffer string = new StringBuffer();
        if (readable()) {
            string.append("r");
        } else {
            string.append("-");
        }
        if (writable()) {
            string.append("w");
        } else {
            string.append("-");
        }
        if (executable()) {
            string.append("x");
        } else {
            string.append("-");
        }
        return string.toString();
    }

}