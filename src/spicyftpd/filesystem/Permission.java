package spicyftpd.filesystem;

/**
*   �t�@�C���V�X�e���Ɉˑ����Ȃ��p�[�~�b�V�������
*/
public final class Permission {
    /// �ǂݎ�苖���
    private final boolean _readable;
    /// �������݋����
    private final boolean _writable;
    /// ���s�����
    private final boolean _executable;

    /**
    *   �R���X�g���N�^
    *   @param  r   �ǂݎ�苖���
    *   @param  w   �������݋����
    *   @param  e   ���s�����
    */
    protected Permission(boolean r,boolean w,boolean e) {
        _readable   = r;
        _writable   = w;
        _executable = e;
    }

    /**
    *   �ǂݎ�苖���
    *   @return true    -   ����
    *           false   -   �s����
    */
    public final boolean readable() {
        return _readable;
    }

    /**
    *   �������݋����
    *   @return true    -   ����
    *           false   -   �s����
    */
    public final boolean writable() {
        return _writable;
    }

    /**
    *   ���s�����
    *   @return true    -   ����
    *           false   -   �s����
    */
    public final boolean executable() {
        return _executable;
    }

    /**
    *   �p�[�~�b�V�������A���X�g�\��(ls)���Ŏg���Ă���悤��
    *   �W���I���t�H�[�}�b�g�ŕ\������B
    *   ��:
    *       rwx
    *       r-x
    *       ---
    *   �t�B�[���h�̈Ӗ�:
    *       r  read (�ǂݎ��)  
    *       w  �������� (�ҏW)  
    *       x  ���s (����)  
    *       -  �Y�����鋖���̔۔F
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