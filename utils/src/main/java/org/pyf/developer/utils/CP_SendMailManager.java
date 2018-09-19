package org.pyf.developer.utils;

public class CP_SendMailManager {

    CP_ISendMail sendMail1 = new CP_SendMail1();
    CP_ISendMail sendMail2 = new CP_SendMail2();
    CP_ISendMail sendMail = sendMail2;

    //public static void main(String[] args) throws InterruptedException {
    //    CP_SendMailManager manager = new CP_SendMailManager();
    //
    //    //for (int i = 0; i < 10; i++) {
    //        manager.setSubject("测试邮件");
    //        manager.setBody("邱思湲");
    //        manager.setTo("hanqunfeng@nq.com");
    //        manager.addFileAffix("/Users/hanqunfeng/idea_workspaces/cpframework/pom.xml");
    //        manager.sendout();
    //    //}
    //}

    public boolean setSubject(String mailSubject) {
        return sendMail.setSubject(mailSubject);
    }

    public boolean setBody(String mailBody) {
        return sendMail.setBody(mailBody);
    }

    public boolean setFrom(String from) {
        return sendMail.setFrom(from);
    }

    public boolean setTo(String to) {
        return sendMail.setTo(to);
    }

    public boolean setTo(String[] to) {
        return sendMail.setTo(to);
    }

    public boolean setCopyTo(String copyto) {
        return sendMail.setCopyTo(copyto);
    }

    public boolean setCopyTo(String[] copyto) {
        return sendMail.setCopyTo(copyto);
    }

    public boolean addFileAffix(String filename) {
        return sendMail.addFileAffix(filename);
    }

    public boolean sendout() {
        boolean result = sendMail.sendout();

        if (sendMail.shouldChange()) {
            if (sendMail instanceof CP_SendMail1) {
                sendMail = sendMail2;
            } else {
                sendMail = sendMail1;
            }
        }
        return result;
    }

}
