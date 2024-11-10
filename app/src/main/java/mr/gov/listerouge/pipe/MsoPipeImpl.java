package mr.gov.listerouge.pipe;

import com.morpho.morphosmart.pipe.Holder;
import com.morpho.morphosmart.pipe.IDeviceEnum;
import com.morpho.morphosmart.pipe.ILog;
import com.morpho.morphosmart.pipe.IMsoPipeConstants;
import com.morpho.morphosmart.pipe.PipeFunctions;
import com.morpho.morphosmart.sdk.IMsoPipe;

import java.util.ArrayList;

public class MsoPipeImpl implements IMsoPipe, IMsoPipeConstants, IDeviceEnum {
    private static IMsoPipe instance;
    private PipeFunctions functions = new PipeFunctions();
    private ILog logger = null;
    private String server_ip = "127.0.0.1";
    private int server_port = 11011;

    public MsoPipeImpl setLogger(ILog iLog) {
        this.logger = iLog;
        return this;
    }

    public void log(String str) {
        ILog iLog = this.logger;
        if (iLog != null) {
            iLog.log("MsoPipeImpl : " + str + "\n");
        }
    }

    public static synchronized IMsoPipe getInstance() {
        IMsoPipe iMsoPipe;
        synchronized (MsoPipeImpl.class) {
            if (instance == null) {
                instance = new MsoPipeImpl();
            }
            iMsoPipe = instance;
        }
        return iMsoPipe;
    }

    public MsoPipeImpl() {
        instance = this;
    }

    public MsoPipeImpl setServer_ip(String str) {
        this.server_ip = str;
        return this;
    }

    public String getServer_ip() {
        return this.server_ip;
    }

    public MsoPipeImpl setServer_port(int i) {
        this.server_port = i;
        return this;
    }

    public int getServer_port() {
        return this.server_port;
    }

    public int clientPipe_CallbackComOpen(Object obj, String str, long j, int i, String str2) {
        log("[INF] Start clientPipe_CallbackComOpen");
        int TCP_Client_Connect = this.functions.TCP_Client_Connect(str, (int) j);
        if (TCP_Client_Connect == 0) {
            TCP_Client_Connect = this.functions.TCP_Send(new byte[]{IMsoPipeConstants.SP_PIPE_TAG_CONNECT_MSO});
        } else {
            log("[ERR] TCP_Client_Connect return " + TCP_Client_Connect);
        }
        if (TCP_Client_Connect == 0) {
            byte[] bytes = str2.getBytes();
            log("[INF] m_auc_MSOSerialNumber length " + bytes.length);
            if (bytes[bytes.length - 1] != 0) {
                byte[] bArr = new byte[(bytes.length + 1)];
                for (int i2 = 0; i2 < bytes.length; i2++) {
                    bArr[i2] = bytes[i2];
                }
                bArr[bytes.length] = 0;
                bytes = bArr;
            }
            log("[INF] Sp_Pipe_Send_LenAndDatas sn.length " + bytes.length);
            TCP_Client_Connect = this.functions.Sp_Pipe_Send_LenAndDatas(bytes);
        } else {
            log("[ERR] TCP_Send SP_PIPE_TAG_CONNECT_MSO Error " + TCP_Client_Connect);
        }
        if (TCP_Client_Connect == 0) {
            TCP_Client_Connect = this.functions.Sp_Pipe_Receive_Responses(new Holder(), 67, i);
            if (TCP_Client_Connect != 0) {
                log("[ERR] Sp_Pipe_Receive_Responses " + TCP_Client_Connect);
            }
        } else {
            log("[ERR] Sp_Pipe_Send_LenAndDatas " + TCP_Client_Connect);
        }
        return TCP_Client_Connect;
    }

    public int clientPipe_CallbackComSend(Object obj, long j, ArrayList<Byte> arrayList) {
        log("[INF] ClientPipe_CallbackComSend sending: " + arrayList.size() + " to server timeout " + j);
        int TCP_Send = this.functions.TCP_Send(new byte[]{IMsoPipeConstants.SP_PIPE_TAG_COMSEND});
        if (TCP_Send == 0) {
            TCP_Send = this.functions.TCP_Send(this.functions.reverseInt(arrayList.size()));
            if (TCP_Send != 0) {
                log("[ERR] TCP_Send length of data arrayList.size() " + arrayList.size() + " l_i_Ret " + TCP_Send);
            }
        } else {
            log("[ERR] TCP_Send SP_PIPE_TAG_COMSEND " + TCP_Send);
        }
        if (TCP_Send == 0) {
            byte[] bArr = new byte[arrayList.size()];
            for (int i = 0; i < bArr.length; i++) {
                bArr[i] = arrayList.get(i).byteValue();
            }
            TCP_Send = this.functions.TCP_Send(bArr);
            if (TCP_Send != 0) {
                log("[ERR] TCP_Send data error " + TCP_Send);
            }
        }
        if (TCP_Send == 0 && (TCP_Send = this.functions.Sp_Pipe_Receive_Responses(new Holder(), 83, (int) j)) != 0) {
            log("[ERR] Sp_Pipe_Receive_Responses error " + TCP_Send);
        }
        return TCP_Send;
    }

    public int clientPipe_CallbackComReceive(Object obj, long j, ArrayList<Byte> arrayList) {
        Holder holder = new Holder();
        int Sp_Pipe_Receive_Responses = this.functions.Sp_Pipe_Receive_Responses(holder, 82, (int) j);

        if (Sp_Pipe_Receive_Responses == 0 && holder.data != null) {
            log("[INF] Sp_Pipe_Receive_Responses received " + Sp_Pipe_Receive_Responses + " bytes");
            for (byte valueOf : (byte[]) holder.data) {
                arrayList.add(Byte.valueOf(valueOf));
            }
        } else if (Sp_Pipe_Receive_Responses != -10000) {
            log("[ERR] Sp_Pipe_Receive_Responses " + Sp_Pipe_Receive_Responses);
        }
        return Sp_Pipe_Receive_Responses;
    }

    public int clientPipe_CallbackComReceiveFree(Object obj, ArrayList<Byte> arrayList) {
        log("[INF] clientPipe_CallbackComReceiveFree");
        return 0;
    }

    public int clientPipe_CallbackComClose(Object obj) {
        log("[INF] clientPipe_CallbackComClose");
        int TCP_Client_Close = this.functions.TCP_Client_Close();
        log("[INF] TCP_Client_Close " + TCP_Client_Close);
        return TCP_Client_Close;
    }

    public int enumerate(ArrayList<String> arrayList) {
        byte b;
        log("[INF] enumerate ");
        byte[] bArr = new byte[1];
        log("[INF] Establishes a connection to a Server, specified by its Address, and it's Port Number " + this.server_ip + ":" + this.server_port);
        int TCP_Client_Connect = this.functions.TCP_Client_Connect(this.server_ip, this.server_port);
        StringBuilder sb = new StringBuilder();
        sb.append("[INF] TCP_Client_Connect l_i_Ret ");
        sb.append(TCP_Client_Connect);
        log(sb.toString());
        if (TCP_Client_Connect == 0) {
            TCP_Client_Connect = this.functions.TCP_Send(new byte[]{IMsoPipeConstants.SP_PIPE_TAG_MSO_LIST});
            if (TCP_Client_Connect != 0) {
                log("[ERR] TCP_Send SP_PIPE_TAG_MSO_LIST " + TCP_Client_Connect);
            }
        } else {
            log("[ERR] TCP_Client_Connect " + TCP_Client_Connect);
        }
        if (TCP_Client_Connect == 0) {
            TCP_Client_Connect = this.functions.TCP_Recv(bArr, bArr.length, 7000);
        } else {
            log("[ERR] TCP_Recv " + TCP_Client_Connect);
        }
        if (bArr[0] == 76) {
            int Sp_Pipe_Receive_Status = TCP_Client_Connect == 0 ? this.functions.Sp_Pipe_Receive_Status() : -1;
            if (Sp_Pipe_Receive_Status == 0) {
                Sp_Pipe_Receive_Status = this.functions.TCP_Recv(bArr, bArr.length, 7000);
                b = bArr[0];
            } else {
                b = 0;
            }
            if (Sp_Pipe_Receive_Status == 0) {
                Holder holder = new Holder();
                for (int i = 0; i < b; i++) {
                    holder.data = null;
                    this.functions.Sp_Pipe_Receive_LenAndDatas(holder);
                    if (holder.data != null) {
                        String str = new String((byte[]) holder.data);
                        log("[INF] Device Serial " + str);
                        arrayList.add(str);
                    }
                }
            }
        } else {
            b = 0;
        }
        this.functions.TCP_Client_Close();
        return b;
    }
}
