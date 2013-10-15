package cn.edu.scau.tvprotal.debug.crypto;

import cn.edu.scau.tvprotal.core.authorize.CryptoUtil;
import cn.edu.scau.tvprotal.debug.Dump;
import java.util.Scanner;
import java.math.BigInteger;
import java.security.spec.RSAPublicKeySpec;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;

public class RSAEncryptCLI
{
    public static void main(String[] args)
    {
    	Scanner scn = new Scanner(System.in);
        try
        {
            // 构成密钥生成器
            KeyFactory kf = KeyFactory.getInstance("RSA");
            while (true)
            {
                System.out.println("e?");
                BigInteger e = scn.nextBigInteger(16);
                System.out.println("m?");
                BigInteger m = scn.nextBigInteger(16);
                System.out.println("plaintext?");
                scn.nextLine();
                String plaintext = scn.nextLine();

                // 构造公钥
                RSAPublicKeySpec ks = new RSAPublicKeySpec(m, e);
                // 生成公钥
                RSAPublicKey key = (RSAPublicKey)kf.generatePublic(ks);

                // 转码及加密
                byte[] input = plaintext.getBytes("utf-8");
                System.out.println("input:");
                Dump.dump(input);

                byte[] output = CryptoUtil.RSAEncrypt(input, key);
                System.out.println("encrypted:");
                Dump.dump(output);
            }
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            scn.close();
        }
    }
}
