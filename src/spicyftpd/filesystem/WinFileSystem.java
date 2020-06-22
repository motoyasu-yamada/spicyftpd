package spicyftpd.filesystem;

import java.io.File;

public class WinFileSystem implements FileSystem {

    /**
    *   所有者の許可情報の許可情報を取得します
    *   @return 許可情報
    */
    public Permission getOwnerPermission(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        return new Permission(true,true,true);
    }

    /**
    *   グループ内の他のユーザーの許可情報の許可情報を取得します
    *   @return 許可情報
    */
    public Permission getGroupPermission(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        return new Permission(true,true,true);
    }

    /**
    *   ファイルに対するアクセス権を持つユーザーすべての
    *   他のユーザーの許可情報の許可情報を取得します
    *   @return 許可情報
    */
    public Permission getAllPermission(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        return new Permission(true,true,true);
    }

    /**
    *   ファイルに対するリンクの数を取得する
    *   @return リンク数
    */
    public int getLink(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        return 1;
    }

    /**
    *   ファイルの所有者名を取得する
    *   @return 所有者名
    */
    public String getOwner(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        return "owner";
    }

    /**
    *   ファイルの所有グループを取得する
    *   @return グループ名
    */
    public String getGroup(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        return "group";
    }

}