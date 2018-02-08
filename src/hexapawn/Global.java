/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hexapawn;

import java.util.ArrayList;

/**
 *
 * @author Ari
 */
 class Global {

   public static int GAMESIZE = 3;
    public static int MAXDEPTH = 1;
    public static ArrayList<Board> losingListSave = new ArrayList();

    public static void setGAMESIZE(int n) {
        GAMESIZE = n;
    }

    public static int getGAMESIZE() {
        return GAMESIZE;
    }
    
    public static void setLosingList(ArrayList<Board> list) {
        losingListSave = list;
    }
    
    public static ArrayList<Board> getLosingList() {
        return losingListSave;
    }

}
