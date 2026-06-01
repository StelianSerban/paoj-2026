package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    private static final String[] STATUS_NAMES = {"PENDING", "PROCESSED", "REJECTED"};

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        new File("output").mkdirs();

        // 1. Citește N tranzacții
        int n = Integer.parseInt(scanner.nextLine().trim());

        // 2. Scrie în fișier binar cu DataOutputStream
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            for (int i = 0; i < n; i++) {
                String[] parts = scanner.nextLine().trim().split("\\s+");
                int id = Integer.parseInt(parts[0]);
                double suma = Double.parseDouble(parts[1]);
                String data = parts[2];
                TipTranzactie tip = TipTranzactie.valueOf(parts[3]);

                // bytes 0-3: id (little-endian)
                dos.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(id).array());

                // bytes 4-11: suma (little-endian)
                dos.write(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(suma).array());

                // bytes 12-21: data (10 chars ASCII, padded cu spatii)
                byte[] dataBytes = new byte[10];
                Arrays.fill(dataBytes, (byte) ' ');
                byte[] dataSrc = data.getBytes("ASCII");
                System.arraycopy(dataSrc, 0, dataBytes, 0, Math.min(dataSrc.length, 10));
                dos.write(dataBytes);

                // byte 22: tip (0=CREDIT, 1=DEBIT)
                dos.write(tip == TipTranzactie.CREDIT ? 0 : 1);

                // byte 23: status (0=PENDING)
                dos.write(0);

                // bytes 24-31: padding
                dos.write(new byte[8]);
            }
        }

        // 3. Procesează comenzi cu RandomAccessFile
        try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {
            while (scanner.hasNextLine()) {
                String linie = scanner.nextLine().trim();
                if (linie.isEmpty()) continue;

                if (linie.startsWith("READ ")) {
                    int idx = Integer.parseInt(linie.substring(5).trim());
                    System.out.println(readRecord(raf, idx));

                } else if (linie.startsWith("UPDATE ")) {
                    String[] parts = linie.substring(7).trim().split("\\s+");
                    int idx = Integer.parseInt(parts[0]);
                    String statusStr = parts[1];
                    int statusByte = Arrays.asList(STATUS_NAMES).indexOf(statusStr);
                    raf.seek((long) idx * RECORD_SIZE + 23);
                    raf.write(statusByte);
                    System.out.println("Updated [" + idx + "]: " + statusStr);

                } else if (linie.equals("PRINT_ALL")) {
                    long numRecords = raf.length() / RECORD_SIZE;
                    for (int i = 0; i < numRecords; i++) {
                        System.out.println(readRecord(raf, i));
                    }
                }
            }
        }

        scanner.close();
    }

    private static String readRecord(RandomAccessFile raf, int idx) throws IOException {
        raf.seek((long) idx * RECORD_SIZE);
        byte[] bytes = new byte[RECORD_SIZE];
        raf.readFully(bytes);

        ByteBuffer bb = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);

        int id = bb.getInt(0);
        double suma = bb.getDouble(4);
        String data = new String(bytes, 12, 10, "ASCII").trim();
        int tipByte = bytes[22] & 0xFF;
        int statusByte = bytes[23] & 0xFF;

        String tip = tipByte == 0 ? "CREDIT" : "DEBIT";
        String status = STATUS_NAMES[statusByte];

        return String.format("[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s",
                idx, id, data, tip, suma, status);
    }
}