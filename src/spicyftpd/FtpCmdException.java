
package spicyftpd;

public class FtpCmdException extends Exception {
    private String _status;

    public FtpCmdException(String status) {
        assert(status != null);
        _status = status;
    }

    public final String getStatus() {
        assert(_status != null);
        return _status;
    }

}
