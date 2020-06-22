package spicyftpd;

import java.io.RandomAccessFile;

/**
*   CHROOT�̃G�~�����[�V�����N���X
*
*   @see    Chroot
*   @author m.yamada
*/
public final class ChrootFile {
    private Chroot _root;

    /**
    *   �R���X�g���N�^
    *   ���ڃC���X�^���X�𐶐����邱�Ƃ͂ł��Ȃ��B
    *   Chroot�N���X�ɂ���Đ��������B
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
    *   chroot���ꂽ���[�g�f�B���N�g������̃p�X���擾����
    *   @return ��΃p�X
    */
    public final String getPath() {
        return null;
    }

    /**
    *   ���̃p�X�̐e�f�B���N�g���̃p�X���擾����B
    *   ���[�g�f�B���N�g���̐e�f�B���N�g�����擾���悤�Ƃ����ꍇ
    *   ���[�g�f�B���N�g���̃p�X��Ԃ��B
    *   @return  �p�X
    */
    public final String getParent() {
    }

    /**
    *   �t�@�C���ւ̃����_���A�N�Z�X�I�u�W�F�N�g���擾����
    *   @param  access  RandomAccessFile�R���X�g���N�^�ɓn��
    *                   �t�@�C���ւ̃A�N�Z�X���@�L�q�B
    */
    public final RandomAccessFile file(String access) {
    }
}

/**
*   chroot�Z�L�����e�B�Ɉᔽ�����ꍇ�̗�O
*/
public class ChrootException extends Exception {
}

/**
*   CHROOT�̃G�~�����[�V�����N���X
*/
public final class Chroot {
    /**
    *   Chroot�������[�g�f�B���N�g��
    */
    private final File _rootPath;

    /**
    *   �R���X�g���N�^
    *   @param  rootPath    ���̃p�X�����[�g��CHROOT�̃G�~�����[�V�������s���B
    *   @exception  NullPointerException        ����rootPath��null
    *               IllegalArgumentException    ����rootPath���w�������p�X�����݂��Ȃ�
    *                                           ����rootPath���w�������p�X���f�B���N�g���ł͂Ȃ�
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
    *   �t�@�C���I�u�W�F�N�g���擾����
    *
    *   @param  path    CHROOT�����[�g�ɂ������΃p�X/��΃p�X
    *                   �p�X��؂�� Win/Unix���ɏ��'/'�ł��B
    */
    public final ChrootFile file(String path) {
        if (path.startsWith('/')) {
        }
        int start = 0;
        int split = path.indexOf('/',start);
        String path1 = 
    }

    /**
    *   �t�@�C���p�X������������
    *
    *   ���΃A�N�Z�X('..')�ɂ�胋�[�g�f�B���N�g���ȉ��ɃA�N�Z�X���悤��
    *   �����ꍇ��ʂւ̃A�N�Z�X�͖�������B
    *
    *   @param  path    CHROOT�����[�g�ɂ������΃p�X/��΃p�X
    */
    public final ChrootFile concat(ChrootFile file,String path) {
    }

    /**
    *   �t�@�C���ւ̃A�N�Z�X�������`�F�b�N����
    *   @param  file    ���̃t�@�C�������[�g�f�B���N�g������
    *                   �A�N�Z�X�\�ȃp�X�ɂ��邩�ǂ���
    *   
    */
    private final void check(File file) throws ChrootException {
    }
}
