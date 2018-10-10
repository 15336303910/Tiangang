package cn.plou.common.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @Author : wangjiwei
 * @Date :2017年11月13日下午1:43:43
 *
 * @Comments : 远程执行Linux 脚本 <br>
 * 
 */
public class ShellHelper {

    static Map<String, ShellHelper> cache = new ConcurrentHashMap<>();

    private String ip;
    private String username;
    private String password;
    // 存cmd执行后输出内容
    private List<String> stdout;

    /**
     * 初始化登录信息
     */
    private ShellHelper(final String ip, final String username, final String password) {
	this.ip = ip;
	this.username = username;
	this.password = password;
	stdout = new ArrayList<String>();
    }
    
    private ShellHelper(final String ip) {
	this.ip = ip;
	this.username = "";
	this.password = "";
	stdout = new ArrayList<String>();
    }

    public static ShellHelper getInstance(final String ip, final String username, final String password) {
	synchronized (cache) {
	    if (!cache.containsKey(ip)) {
		cache.put(ip, new ShellHelper(ip, username, password));
	    }
	}
	return cache.get(ip);
    }

    /**
     * 执行shell命令
     */
    public int execute(final String command) {
	int returnCode = 0;
	JSch jsch = new JSch();

	try {
	    // jsch.addIdentity("/root/.ssh/id_rsa");
	    // 创建session并且打开连接，因为创建session之后要主动打开连接
	    Session session = jsch.getSession(username, ip, 22);
	    session.setConfig("StrictHostKeyChecking", "no");// 去掉严格的安全检查
	    session.setPassword(password);

	    // session.setUserInfo(userInfo);
	    session.connect();

	    // 打开通道，设置通道类型，和执行的命令
	    Channel channel = session.openChannel("exec");
	    ChannelExec channelExec = (ChannelExec) channel;
	    channelExec.setCommand(command);

	    channelExec.setInputStream(null);
	    BufferedReader input = new BufferedReader(
		    new InputStreamReader(channelExec.getInputStream()));

	    channelExec.connect();
	    System.out.println("The remote command is :" + command);

	    // 接收远程服务器执行命令的结果
	    String line;
	    while ((line = input.readLine()) != null) {
		stdout.add(line);
	    }
	    input.close();

	    // 得到returnCode
	    if (channelExec.isClosed()) {
		returnCode = channelExec.getExitStatus();
	    }

	    // 关闭通道
	    channelExec.disconnect();
	    // 关闭session
	    session.disconnect();

	} catch (JSchException e) {
	    e.printStackTrace();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return returnCode;
    }

    /**
     * get stdout
     */
    public List<String> getStandardOutput() {
	return stdout;
    }

    public static void main(final String[] args) {
	ShellHelper shell = new ShellHelper("192.168.1.52", "root", "SQL2008sa");
	// shell.execute("echo $JAVA_HOME");
	// shell.execute("echo helo");
	shell.execute(
		"sh /root/scripts/heelo.sh --ip 192.168.1.46 --zookeeper 192.168.1.46:2181,192.168.1.47:2181");

	List<String> stdout = shell.getStandardOutput();
	for (String str : stdout) {
	    System.out.println(str);
	}
	/**
	 * The remote command is :sh /root/scripts/heelo.sh false success
	 */
    }
}