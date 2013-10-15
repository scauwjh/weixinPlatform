package cn.edu.scau.tvprotal.debug.crypto;

import cn.edu.scau.tvprotal.core.authorize.CryptoUtil;
import cn.edu.scau.tvprotal.debug.Dump;
import java.util.Scanner;
import java.math.BigInteger;
import java.security.spec.RSAPrivateKeySpec;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;

public class RSADecryptCLI
{
    public static void main(String[] args)
    {
        try
        {
            Scanner scn = new Scanner(System.in);
            // 构成密钥生成器
            KeyFactory kf = KeyFactory.getInstance("RSA");
            while (true)
            {
                System.out.println("d?");
                BigInteger d = scn.nextBigInteger(16);
                System.out.println("m?");
                BigInteger m = scn.nextBigInteger(16);
                System.out.println("ciphertext?");
                scn.nextLine();
                String ciphertext = scn.nextLine();

                // 构造公钥
                RSAPrivateKeySpec ks = new RSAPrivateKeySpec(m, d);
                // 生成公钥
                RSAPrivateKey key = (RSAPrivateKey)kf.generatePrivate(ks);

                // 转码及加密
                BigInteger bi = new BigInteger(ciphertext, 16);
                byte[] input = bi.toByteArray();
                System.err.println("input:");
                Dump.dump(input);

                byte[] output = CryptoUtil.RSADecrypt(input, key);
                System.err.println("decrypted:");
                Dump.dump(output);

                try
                {
                    String utf8 = new String(output, "UTF-8");
                    System.err.println("tostring:");
                    System.err.println(utf8);
                }
                catch (Exception ex)
                {
                	scn.close();
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
