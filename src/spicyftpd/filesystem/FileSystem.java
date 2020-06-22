package spicyftpd.filesystem;

import java.io.File;

/**
*   Java標準のjava.io.fileではえられない
*   ファイルシステムに依存する拡張情報を
*   取得するためのインターフェース。
*/
public interface FileSystem {

    /**
    *   所有者の許可情報の許可情報を取得します
    *   @return 許可情報
    */
    public Permission getOwnerPermission(File file);

    /**
    *   グループ内の他のユーザーの許可情報の許可情報を取得します
    *   @return 許可情報
    */
    public Permission getGroupPermission(File file);

    /**
    *   ファイルに対するアクセス権を持つユーザーすべての
    *   他のユーザーの許可情報の許可情報を取得します
    *   @return 許可情報
    */
    public Permission getAllPermission(File file);

    /**
    *   ファイルに対するリンクの数を取得する
    *   @return リンク数
    */
    public int getLink(File file);

    /**
    *   ファイルの所有者名を取得する
    *   @return 所有者名
    */
    public String getOwner(File file);

    /**
    *   ファイルの所有グループを取得する
    *   @return グループ名
    */
    public String getGroup(File file);

}