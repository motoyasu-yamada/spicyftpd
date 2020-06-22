package spicyftpd.filesystem;

import java.io.File;

/**
*   Java�W����java.io.file�ł͂����Ȃ�
*   �t�@�C���V�X�e���Ɉˑ�����g������
*   �擾���邽�߂̃C���^�[�t�F�[�X�B
*/
public interface FileSystem {

    /**
    *   ���L�҂̋����̋������擾���܂�
    *   @return �����
    */
    public Permission getOwnerPermission(File file);

    /**
    *   �O���[�v���̑��̃��[�U�[�̋����̋������擾���܂�
    *   @return �����
    */
    public Permission getGroupPermission(File file);

    /**
    *   �t�@�C���ɑ΂���A�N�Z�X���������[�U�[���ׂĂ�
    *   ���̃��[�U�[�̋����̋������擾���܂�
    *   @return �����
    */
    public Permission getAllPermission(File file);

    /**
    *   �t�@�C���ɑ΂��郊���N�̐����擾����
    *   @return �����N��
    */
    public int getLink(File file);

    /**
    *   �t�@�C���̏��L�Җ����擾����
    *   @return ���L�Җ�
    */
    public String getOwner(File file);

    /**
    *   �t�@�C���̏��L�O���[�v���擾����
    *   @return �O���[�v��
    */
    public String getGroup(File file);

}