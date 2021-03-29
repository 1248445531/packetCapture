package packetCapture;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

import java.io.IOException;

public class JpCapMain implements Runnable {
    JpCapFrame frame;
    JpcapCaptor jpcap = null;
    private static Thread thread = null;
    private static boolean pause = true;

    public JpCapMain() {
        //创建界面
        frame = new JpCapFrame();
        frame.setVisible(true);

        //绑定网络设备
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();

        int caplen = 1512;
        boolean promiscCheck = true;

        /*
        WIFI:3
        有线：1
        (不同设备对应的不一样)
        */
        int device = 1;
        try {
            jpcap = JpcapCaptor.openDevice(devices[device], caplen, promiscCheck, 50);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.getCheckBtn().addActionListener(e -> {
            frame.getShowArea().append("当前设备全部网络设备信息为： \n");

            for (NetworkInterface n : devices) {
                System.out.println(n.name + "     |     " + n.description);
                frame.getShowArea().append(n.name + "     |     " + n.description + "\n");
            }
            //System.out.println("-------------------------------------------");
            frame.getShowArea().append(printSeparator(110, 0));
            frame.getShowArea().append("\n当前使用网卡信息： " + devices[device].name + "     |     " + devices[device].description + "\n");
            frame.getShowArea().append(printSeparator(110, 1));
        });

        frame.getStartBtn().addActionListener(e -> {
            if (pause) {
                if (thread == null) {
                    frame.getShowArea().append("   开始抓包,抓取范围为：" + JpCapFrame.getFilterField().getText() + " ……\n");
                    thread = new Thread(this);
                    thread.setPriority(Thread.MIN_PRIORITY);
                    //thread.sleep(100);
                    thread.start();
                    pause = false;
                    frame.getStartBtn().setText("暂停");
                } else {
                    frame.getStartBtn().setText("暂停");
                    pause = false;
                    frame.getShowArea().append("   继续抓包,抓取范围为：" + JpCapFrame.getFilterField().getText() + " ……\n");
                    synchronized (thread) {
                        thread.notify();
                    }
                }
            } else {
                pause = true;
                frame.getStartBtn().setText("开始");
                frame.getShowArea().append("    暂停抓包\n");
            }
        });

        frame.getClearBtn().addActionListener(e -> {
            frame.getShowArea().setText("");
            frame.getModel().setRowCount(0);
        });

        frame.getExitBtn().addActionListener(e -> {
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        new JpCapMain();
    }

    @Override
    public void run() {
        try {
            new JpCapPacket(jpcap).capture();
            thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param separator "-"的数量
     * @param line      "\n"的数量
     * @return
     */
    public String printSeparator(int separator, int line) {
        String s = "";
        String l = "";

        for (int i = 0; i < separator; i++) {
            s += "-";
        }

        for (int i = 0; i < line; i++) {
            l += "\n";
        }
        return s + l;
    }

    public static Thread getThread() {
        return thread;
    }

    public static boolean isPause() {
        return pause;
    }
}