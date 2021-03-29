package packetCapture;

import jpcap.JpcapCaptor;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

import java.sql.Timestamp;
import java.util.Vector;

public class JpCapPacket {
    private JpcapCaptor jpcap;

    public JpCapPacket(JpcapCaptor jpcap) {
        this.jpcap = jpcap;
    }

    void capture() throws InterruptedException {
        int i = 0;
        while (true) {
            synchronized (JpCapMain.getThread()) {
                if (JpCapMain.isPause()) {
                    JpCapMain.getThread().wait();
                }
            }
            Packet packet = jpcap.getPacket();
            if (packet instanceof IPPacket && ((IPPacket) packet).version == 4) {
                i++;
                IPPacket ip = (IPPacket) packet;//强转

//                System.out.println("版本：IPv4");
//                System.out.println("优先权：" + ip.priority);
//                System.out.println("区分服务：最大的吞吐量： " + ip.t_flag);
//                System.out.println("区分服务：最高的可靠性：" + ip.r_flag);
//                System.out.println("长度：" + ip.length);
//                System.out.println("标识：" + ip.ident);
//                System.out.println("DF:Don't Fragment: " + ip.dont_frag);
//                System.out.println("NF:Nore Fragment: " + ip.more_frag);
//                System.out.println("片偏移：" + ip.offset);
//                System.out.println("生存时间：" + ip.hop_limit);

                String protocol = "";
                switch (new Integer(ip.protocol)) {
                    case 1:
                        protocol = "ICMP";
                        break;
                    case 2:
                        protocol = "IGMP";
                        break;
                    case 6:
                        protocol = "TCP";
                        break;
                    case 8:
                        protocol = "EGP";
                        break;
                    case 9:
                        protocol = "IGP";
                        break;
                    case 17:
                        protocol = "UDP";
                        break;
                    case 41:
                        protocol = "IPv6";
                        break;
                    case 89:
                        protocol = "OSPF";
                        break;
                    default:
                        break;
                }
//                System.out.println("协议：" + protocol);
//                System.out.println("源IP " + ip.src_ip.getHostAddress());
//                System.out.println("目的IP " + ip.dst_ip.getHostAddress());
//                System.out.println("源主机名： " + ip.src_ip);
//                System.out.println("目的主机名： " + ip.dst_ip);
//                System.out.println("----------------------------------------------");
                String filterInput = JpCapFrame.getFilterField().getText();
                if (filterInput.equals(ip.src_ip.getHostAddress()) ||
                        filterInput.equals(ip.dst_ip.getHostAddress()) ||
                        filterInput.equals(protocol) ||
                        filterInput.equals("")) {
                    Vector dataVector = new Vector();
                    Timestamp timestamp = new Timestamp((packet.sec * 1000) + (packet.usec / 1000));

                    dataVector.addElement(i + "");
                    //dataVector.addElement(new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss").format(new Date()));
                    dataVector.addElement(timestamp.toString());//数据包时间
                    dataVector.addElement(ip.src_ip.getHostAddress());
                    dataVector.addElement(ip.dst_ip.getHostAddress());
                    dataVector.addElement(protocol);
                    dataVector.addElement(packet.data.length);

                    String strtmp = "";
                    for (int j = 0; j < packet.data.length; j++) {
                        strtmp += Byte.toString(packet.data[j]);
                    }
                    dataVector.addElement(strtmp); //数据内容

                    JpCapFrame.getModel().addRow(dataVector);
                }
            }
        }
    }
}
