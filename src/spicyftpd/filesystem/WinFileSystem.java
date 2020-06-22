package spicyftpd.filesystem;

import java.io.File;

public class WinFileSystem implements FileSystem {

    /**
    *   ���L�҂̋����̋������擾���܂�
    *   @return �����
    */
    public Permission getOwnerPermission(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        return new Permission(true,true,true);
    }

    /**
    *   �O���[�v���̑��̃��[�U�[�̋����̋������擾���܂�
    *   @return �����
    */
    public Permission getGroupPermission(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        return new Permission(true,true,true);
    }

    /**
    *   �t�@�C���ɑ΂���A�N�Z�X���������[�U�[���ׂĂ�
    *   ���̃��[�U�[�̋����̋������擾���܂�
    *   @return �����
    */
    public Permission getAllPermission(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        return new Permission(true,true,true);
    }

    /**
    *   �t�@�C���ɑ΂��郊���N�̐����擾����
    *   @return �����N��
    */
    public int getLink(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        return 1;
    }

    /**
    *   �t�@�C���̏��L�Җ����擾����
    *   @return ���L�Җ�
    */
    public String getOwner(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        return "owner";
    }

    /**
    *   �t�@�C���̏��L�O���[�v���擾����
    *   @return �O���[�v��
    */
    public String getGroup(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        return "group";
    }

}