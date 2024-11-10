package com.morpho.morphosmart.pipe;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

public class PipeFunctions implements IMsoPipeConstants {
    private boolean finish = false;
    private Socket socket;

    public void setFinish(boolean z) {
        this.finish = z;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void setSocket(Socket socket2) {
        this.socket = socket2;
    }

    public int Sp_Pipe_Receive_LenAndDatas(Holder<byte[]> holder) {
        byte[] bArr = new byte[4];
        int TCP_Recv = TCP_Recv(bArr, 4, MAX_RECV_TIME);
        if (TCP_Recv != 0) {
            return TCP_Recv;
        }
        int reverse4Byte = reverse4Byte(bArr);
        if (reverse4Byte == 0) {
            holder.data = null;
            return TCP_Recv;
        }
        holder.data = new byte[reverse4Byte];
        return TCP_Recv((byte[]) holder.data, reverse4Byte, MAX_RECV_TIME);
    }

    public int Sp_Pipe_Receive_Status() {
        byte[] bArr = new byte[4];
        int TCP_Recv = TCP_Recv(bArr, 4, MAX_RECV_TIME);
        return TCP_Recv == 0 ? reverse4Byte(bArr) : TCP_Recv;
    }

    public int Sp_Pipe_Receive_Responses(Holder<byte[]> holder, int i, int i2) {
        int TCP_Recv;
        byte[] bArr = new byte[10];
        while (true) {
            TCP_Recv = TCP_Recv(bArr, 1, i2);
            byte b = bArr[0];
            if (TCP_Recv == 0) {
                PrintStream printStream = System.out;
                printStream.println("PIEP_FUNCTIONS : Sp_Pipe_Receive_Responses received type " + b);
                if (b == 82) {
                    Sp_Pipe_Receive_LenAndDatas(holder);
                    if (b != i) {
                        PrintStream printStream2 = System.out;
                        printStream2.println("PIEP_FUNCTIONS : l_c_Tag != desiredTag Sp_Pipe_Receive_Responses Sp_Pipe_Receive_LenAndDatas  " + ((byte[]) holder.data).length);
                    }
                }
                if (b == 83) {
                    TCP_Recv = Sp_Pipe_Receive_Status();
                    PrintStream printStream3 = System.out;
                    printStream3.println("PIEP_FUNCTIONS : Sp_Pipe_Receive_Responses Sp_Pipe_Receive_Status  " + TCP_Recv);
                }
                if (b == 67) {
                    TCP_Recv = Sp_Pipe_Receive_Status();
                    continue;
                }
                if (i == b) {
                    break;
                }
            } else {
                PrintStream printStream4 = System.out;
                printStream4.println("PIEP_FUNCTIONS : Sp_Pipe_Receive_Responses TCP_Recv Err: " + TCP_Recv);
                break;
            }
        }
        return TCP_Recv;
    }

    public int Sp_Pipe_Send_TagAndStatus(byte b, int i) {
        byte[] bArr = new byte[5];
        int i2 = 0;
        bArr[0] = b;
        byte[] reverseInt = reverseInt(i);
        while (i2 < reverseInt.length) {
            int i3 = i2 + 1;
            bArr[i3] = reverseInt[i2];
            i2 = i3;
        }
        return TCP_Send(bArr);
    }

    public int Sp_Pipe_Send_LenAndDatas(byte[] bArr) {
        byte[] bArr2 = new byte[4];
        int TCP_Send = TCP_Send(reverseInt(bArr.length));
        return TCP_Send == 0 ? TCP_Send(bArr) : TCP_Send;
    }

    public int TCP_Recv(byte[] bArr, int i, int i2) {
        if (this.finish) {
            return -1;
        }
        long j = 0;
        try {
            this.socket.setSoTimeout(i2);
            if (i2 > 0) {
                j = System.currentTimeMillis() + ((long) i2);
            }
            InputStream inputStream = this.socket.getInputStream();
            int i3 = 0;
            do {
                int read = inputStream.read(bArr, i3, i - i3);
                if (read > 0) {
                    i3 += read;
                }
                if (read < 0) {
                    return -1;
                }
                if (i2 > 0 && j < System.currentTimeMillis()) {
                    return -10000;
                }
            } while (i3 < i);
            if (i3 >= i) {
                return 0;
            }
            return 1;
        } catch (SocketTimeoutException unused) {
            return -10000;
        } catch (SocketException e) {
            e.printStackTrace();
            return 1;
        } catch (IOException e2) {
            e2.printStackTrace();
            return 1;
        } catch (Exception e3) {
            e3.printStackTrace();
            return 1;
        }
    }

    public int TCP_Send(byte[] bArr) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            dataOutputStream.write(bArr);
            if (bArr.length == dataOutputStream.size()) {
                return 0;
            }
            PrintStream printStream = System.out;
            printStream.println("PIPE_FUNCTION ERROR: TCP_Send data.length " + bArr.length + " send: len " + dataOutputStream.size());
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public int TCP_Client_Close() {
        try {
            if (this.socket == null) {
                return 0;
            }
            this.socket.close();
            return 0;
        } catch (Exception e) {
            PrintStream printStream = System.out;
            printStream.println("PIPE_FUNCTION ERROR: TCP_Client_Close " + e.getMessage());
            return 1;
        }
    }

    public int TCP_Client_Connect(String str, int i) {
        try {
            this.socket = new Socket(InetAddress.getByName(str), i);
            return 0;
        } catch (Exception e) {
            PrintStream printStream = System.out;
            printStream.println("PIPE_FUNCTION ERROR: TCP_Client_Connect " + e.getMessage());
            return 1;
        }
    }

    public int reverse4Byte(byte[] bArr) {
        for (int i = 0; i < 2; i++) {
            byte b = bArr[i];
            int i2 = 3 - i;
            bArr[i] = bArr[i2];
            bArr[i2] = b;
        }
        return ByteBuffer.wrap(bArr).getInt();
    }

    public byte[] reverseInt(int i) {
        byte[] array = ByteBuffer.allocate(4).putInt(i).array();
        byte[] bArr = new byte[4];
        for (int i2 = 0; i2 < 4; i2++) {
            bArr[i2] = array[3 - i2];
        }
        return bArr;
    }
}
