package spicyftpd.filesystem;

public class FileSystemFactory {

    /**
    *   ���݂̃V�X�e�����ɉ������A
    *   �t�@�C���V�X�e���I�u�W�F�N�g���쐬����B
    *   @return ���݂̃V�X�e�����ɉ������t�@�C���V�X�e���I�u�W�F�N�g
    */
    public static final FileSystem getInstance() {
        return new WinFileSystem();
    }

}