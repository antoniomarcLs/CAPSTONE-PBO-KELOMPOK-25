package com.mycompany.feedbackfiber.model;

public enum IdentityMethod {
    AKUN_FIBER("Akun FIBER", "Username akun FIBER", "user_account", "username"),
    EMAIL("Alamat Email", "Alamat email", "user_email", "email"),
    TELEPON("Nomor Telepon", "Nomor telepon", "user_phone", "nomor_hp");

    public final String label;
    public final String fieldLabel;
    public final String table;
    public final String column;

    IdentityMethod(String label, String fieldLabel, String table, String column) {
        this.label = label;
        this.fieldLabel = fieldLabel;
        this.table = table;
        this.column = column;
    }
}
