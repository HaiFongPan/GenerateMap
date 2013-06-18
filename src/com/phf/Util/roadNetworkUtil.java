package com.phf.Util;

/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 13-5-2
 * Time: 下午9:39
 * Project: GenerateRN
 */
public class roadNetworkUtil {

    public static boolean stepOne(long N[][]){
        long nc = N[1][1] + N[1][2] + N[1][3] +
                 N[2][1] + N[2][2] + N[2][3] +
                 N[3][1] + N[3][2] + N[3][3];
        if( nc >= 2 && nc <= 6)
            return true;
        return false;
    }

    public static boolean stepTwo(long N[][]){
        int nc = 0;
        if(N[1][2] == 0 && N[1][1] == 1) nc++;
        if(N[1][1] == 0 && N[2][1] == 1) nc++;
        if(N[2][1] == 0 && N[3][1] == 1) nc++;
        if(N[3][1] == 0 && N[3][2] == 1) nc++;
        if(N[3][2] == 0 && N[3][3] == 1) nc++;
        if(N[3][3] == 0 && N[2][3] == 1) nc++;
        if(N[2][3] == 0 && N[1][3] == 1) nc++;
        if(N[1][3] == 0 && N[1][2] == 1) nc++;
        if( nc == 1)
            return true;
        return false;
    }

    public static boolean stepThree(long N[][]){
        if(N[1][2] * N[2][1] * N[2][3] == 1)
            return true;
        else
        {
            int nc = 0;
            if(N[0][2] == 0 && N[0][1] == 1) nc++;
            if(N[0][1] == 0 && N[1][1] == 1) nc++;
            if(N[1][1] == 0 && N[2][1] == 1) nc++;
            if(N[2][1] == 0 && N[2][2] == 1) nc++;
            if(N[2][2] == 0 && N[2][3] == 1) nc++;
            if(N[2][3] == 0 && N[1][3] == 1) nc++;
            if(N[1][3] == 0 && N[0][3] == 1) nc++;
            if(N[0][3] == 0 && N[0][2] == 1) nc++;
            if (nc != 1)
                return true;
            return false;
        }
    }

    public static boolean stepFour(long N[][]){
        if(N[1][2] * N[2][1] * N[3][2] == 0)
            return true;
        else
        {
            int nc = 0;
            if(N[1][1] == 0 && N[1][0] == 1) nc++;
            if(N[1][0] == 0 && N[2][0] == 1) nc++;
            if(N[2][0] == 0 && N[2][0] == 1) nc++;
            if(N[3][0] == 0 && N[2][1] == 1) nc++;
            if(N[3][1] == 0 && N[2][2] == 1) nc++;
            if(N[3][2] == 0 && N[1][2] == 1) nc++;
            if(N[2][2] == 0 && N[0][2] == 1) nc++;
            if(N[1][2] == 0 && N[0][1] == 1) nc++;
            if (nc != 1)
                return true;
            return false;
        }
    }
}
