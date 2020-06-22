package spicyftpd;

/*
*
*   FTPスレッド
*   レスポンスコード メッセージ例 意味 

public class FtpResponse {

    /// RESTコマンドのためのマーカー返答である 
    public final static int STATUS_RESTART_MARKER_REPLY_        = 110;
    public final static String STATUSS = "Restart marker reply";
    /// サービスは停止しているが、nnn分後に準備できる 
    public final static int STATUS_SERVICE_READY_IN_MINUTES     = 120;
    public final static String STATUSS = ""Service ready in nnn minutes"";
    /// データコネクションはすでに確立されている。このコネクションで転送を開始する 
    public final static int STATUS_DATA_CONNECTION_ALREADY_OPEN = 125;
    public final static String STATUSS = ""Data connection already open; transfer starting"";
    /// ファイルステータスは正常である。データコネクションを確立する 
    public final static int STATUS_FILE_STATUS_OKAY             = 150;
    public final static String STATUSS = "File status okay; about to open data connection";
    /// コマンドは正常に受け入れられた 
    public final static int STATUS_COMMAND_OKAY = 200;
    public final static String STATUSS = "Command okay";
    /// コマンドは実装されていない。SITEコマンドでOSコマンドが適切でない場合など 
    public final static int STATUS_COMMAND_NOT_IMPLEMENTED = 202;
    public final static String STATUSS = "Command not implemented, superfluous at this site";
    /// STATコマンドに対するレスポンス 
    public final static int STATUS_STAT_SYSTEM_STATUS = 211;
    public final static String STATUSS = "System status, or system help reply";
    /// STATコマンドによるディレクトリ情報を示す 
    public final static int STATUS_STAT_DIRECTORY = 212;
    public final static String STATUSS = "Directory status";
    /// STATコマンドによるファイル情報を示す 
    public final static int STATUS_STAT_FILE     = 213;
    public final static String STATUSS_FILE_STATUS = "File status";
    /// HELPコマンドに対するレスポンス 
    public final static int STATUS_HELP_MESSAGE = 214;
    public final static String STATUSS = "Help message";
    /// SYSTコマンドに対するレスポンス 
    public final static int STATUS_NAME_SYSTEM_TYPE = 215;
    public final static String STATUSS = "NAME system type";
    /// 新規ユーザー向けに準備が整った。ログイン時に表示される場合を想定している 
    public final static int STATUS_SERVICE_READY = 220;
    public final static String STATUSS = "Service ready for new user";
    /// コントロールコネクションを切断する。QUITコマンド時のレスポンス 
    public final static int STATUS_SERVICE_CLOSING_CONTROL_CONNECTION = 221;
    public final static String STATUSS = "Service closing control connection";
    /// データコネクションを確立した。データの転送は行われていない 
    public final static int STATUS_DATA_CONNECTION_OPEN = 225;
    public final static String STATUSS = "Data connection open; no transfer in progress";
    /// 要求されたリクエストは成功した。データコネクションをクローズする 
    public final static int STATUSI = 226;
    public final static String STATUSS = "Closing data connection";
    /// PASVコマンドへのレスポンス。h1～h4はIPアドレス、p1～p2はポート番号を示す 
    public final static int STATUSI = 227;
    public final static String STATUSS = "Entering Passive Mode (h1,h2,h3,h4,p1,p2)";
    /// ユーザーログインの成功 
    public final static int STATUSI = 230;
    public final static String STATUSS = "User logged in, proceed";
    /// 要求されたコマンドによる操作は正常終了した 
    public final static int STATUSI = 250;
    public final static String STATUSS = "Requested file action okay, completed";
    /// ファイルやディレクトリを作成したというのがRFCでの意味だが、MKDコマンドの結果以外にも、実際にはPWDコマンドの結果にも用いられる 
    public final static int STATUSI = 257;
    public final static String STATUSS = ""PATHNAME" created";
    /// パスワードの入力を求める 
    public final static int STATUSI = 331;
    public final static String STATUSS = "User name okay, need password";
    /// ACCTコマンドで課金情報を指定する必要がある 
    public final static int STATUSI = 332;
    public final static String STATUSS = "Need account for login";
    *   350 Requested file action pending further information 他の何らかの情報を求めている 
    /// サービスを提供できない。コントロールコネクションを終了する。サーバのシャットダウン時など 
    public final static int STATUSI = 421;
    public final static String STATUSS = "Service not available, closing control connection";
    /// データコネクションをオープンできない 
    public final static int STATUSI = 425;
    public final static String STATUSS = "Can't open data connection";
    /// 何らかの原因により、コネクションをクローズし、データ転送も中止した 
    public final static int STATUS_CONNECTION_CLOSED_TRANSFER_ABORTED = 426;
    public final static String STATUSS = "Connection closed; transfer aborted";
    /// 要求されたリクエストはアクセス権限やファイルシステムの理由で実行できない 
    public final static int STATUSI = 450;
    public final static String STATUSS = "Requested file action not taken";
    /// ローカルエラーのため処理を中止した 
    public final static int STATUSI = 451;
    public final static String STATUSS = "Requested action aborted. Local error in processing";
    /// ディスク容量の問題で実行できない 
    public final static int STATUSI = 452;
    public final static String STATUSS = "Requested action not taken";
    /// コマンドの文法エラー 
    public final static int STATUSI = 500;
    public final static String STATUSS = "Syntax error, command unrecognized";
    /// 引数やパラメータの文法エラー 
    public final static int STATUSI = 501;
    public final static String STATUSS = "Syntax error in parameters or arguments";
    /// コマンドは未実装である 
    public final static int STATUSI = 502;
    public final static String STATUSS = "Command not implemented";
    /// コマンドを用いる順番が間違っている 
    public final static int STATUS_BAD_SEQUENCE_OF_COMMANDS = 503;
    public final static String STATUSS = "Bad sequence of commands";
    /// 引数やパラメータが未実装 
    public final static int STATUS_PARAMETER_OF_COMMAND_NOT_IMPLEMENTED_ = 504;
    public final static String STATUSS = "Command not implemented for that parameter";
    /// ユーザーはログインできなかった 
    public final static int STATUS_NOT_LOGGED_IN = 530;
    public final static String STATUSS = "Not logged in";
    /// ファイル送信には、ACCTコマンドで課金情報を確認しなくてはならない 
    public final static int STATUSI = 532;
    public final static String STATUSS = "Need account for storing files";
    /// 要求されたリクエストはアクセス権限やファイルシステムの理由で実行できない 
    public final static int STATUSI = 550;
    public final static String STATUSS = "Requested action not taken";
    /// ページ構造のタイプの問題で実行できない 
    public final static int STATUS_REQUESTED_ACTION_ABORTED_PAGE_TYPE_UNKNOWN = 551;
    public final static String STATUSS = "Requested action aborted. Page type unknown";
    /// ディスク容量の問題で実行できない 
    public final static int STATUS_REQUESTED_FILE_ACTION_ABORTED = 552;
    public final static String STATUSS = "Requested file action aborted";
    /// ファイル名が間違っているため実行できない 
    public final static int STATUS_REQUESTED_ACTION_NOT_TAKEN    = 553;
    public final static String STATUSS = "Requested action not taken";
};
*/
