package com.pao.proiect.catalog.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

public class AuditService {
    private static AuditService instance;
    private static final String AUDIT_FILE = "audit.csv";
    private final ReentrantLock lock = new ReentrantLock();

    private AuditService() {}

    public static synchronized AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public void log(String numeActiune) {
        lock.lock();
        try (PrintWriter pw = new PrintWriter(new FileWriter(AUDIT_FILE, true))) {
            pw.println(numeActiune + "," + LocalDateTime.now());
            System.out.println("[AUDIT] Scrie in: " + new java.io.File(AUDIT_FILE).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("[AUDIT] Eroare la scriere in audit.csv: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}