/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.niti;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import server.kontroler.Kontroler;

/**
 *
 * @author Korisnik
 */
public class ServerNit extends Thread{
        ServerSocket serverSocket;
    List<KlijentNit> klijentskeNiti;

    public ServerNit(ServerSocket serverSocket) {
        klijentskeNiti = new ArrayList<>();
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Klijent se povezao");
                KlijentNit k = new KlijentNit(socket, this);
                k.start();
                klijentskeNiti.add(k);
            } catch (IOException ex) {
             }
        }
        zaustaviKlijentskeNiti();
    }

    private void zaustaviKlijentskeNiti() {
        for (KlijentNit klijentskaNit : klijentskeNiti) {
            klijentskaNit.zaustavi();
        }
    }

    public void zaustavi() throws IOException {
        serverSocket.close();
    }

    void odjavi(KlijentNit aThis) {
        for (KlijentNit k1 : klijentskeNiti) {
            if (aThis.equals(k1)) {
                klijentskeNiti.remove(k1);
                Kontroler.getInstanca().izbrisiKorisnikaIzTabele(aThis.getTrenutAgent());
            }
        }
    }
}
