package com.morpho.morphosmart.sdk;

import java.util.ArrayList;

public interface IMsoPipe {
    int clientPipe_CallbackComClose(Object obj);

    int clientPipe_CallbackComOpen(Object obj, String str, long j, int i, String str2);

    int clientPipe_CallbackComReceive(Object obj, long j, ArrayList<Byte> arrayList);

    int clientPipe_CallbackComReceiveFree(Object obj, ArrayList<Byte> arrayList);

    int clientPipe_CallbackComSend(Object obj, long j, ArrayList<Byte> arrayList);
}
