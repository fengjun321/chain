package org.sparkera.nft;




import org.junit.jupiter.api.Test;
import org.sparkera.nft.config.RebateConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.utils.Numeric;


import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.sparkera.nft.utils.BSCUtils.getWeb3j;

@SpringBootTest
class SparkEraNftApplicationTests {

  // @Test
  //  void contextLoads() {
  //  }

    /** 自定义进制(0,1没有加入,容易与o,l混淆) */
    private static final char[] r = new char[] { 'Q', 'W', 'E', '8', 'A', 'S', '2', 'D', 'Z', 'X', '9', 'C', '7', 'P',
            '5', 'K', '3', 'M', 'J', 'U', 'F', 'R', '4', 'V', 'Y', 'L', 'T', 'N', '6', 'B', 'G' };

    /** (不能与自定义进制有重复) */
    private static final char b = 'H';

    /**
     * 进制长度
     */
    private static final int binLen = r.length;

    /**
     * 序列最小长度
     */
    private static final int s = 6;

    /**
     * 根据ID生成六位随机码
     *
     * @param id ID
     * @return 随机码
     */
    public static String toSerialCode(long id) {
        char[] buf = new char[30];
        int charPos = 30;
        while ((id / binLen) > 0) {
            int ind = (int) (id % binLen);
            buf[--charPos] = r[ind];
            id /= binLen;
        }
        buf[--charPos] = r[(int) (id % binLen)];
        String str = new String(buf, charPos, (30 - charPos));
        // 不够长度的自动随机补全
        if (str.length() < s) {
            StringBuilder sb = new StringBuilder();
            sb.append(b);
            Random rnd = new Random();
            for (int i = 1; i < s - str.length(); i++) {
                sb.append(r[rnd.nextInt(binLen)]);
            }
            str += sb.toString();
        }
        return str;
    }

    public static long codeToId(String code) {
        char chs[] = code.toCharArray();
        long res = 0L;
        for (int i = 0; i < chs.length; i++) {
            int ind = 0;
            for (int j = 0; j < binLen; j++) {
                if (chs[i] == r[j]) {
                    ind = j;
                    break;
                }
            }
            if (chs[i] == b) {
                break;
            }
            if (i > 0) {
                res = res * binLen + ind;
            } else {
                res = ind;
            }
        }
        return res;
    }

    public static Long generateInvitationCode(){

        Date date = Calendar.getInstance().getTime();

        SimpleDateFormat sdf2 = new SimpleDateFormat("YYMMdd HH:mm:ss");

        String dateStr = sdf2.format(date);

        return Long.valueOf(date.getTime());

    }

    public static String getCode() {
        String code = generateCode();
        //User user = userDao.selectByCode(code);
        Integer user = null;
        if(user != null){
            return getCode();
        }else{
            return code ;
        }
    }


    public static String generateCode() {
        String charList = "ABCDEFGHIJKLMNPQRSTUVWXY";
        String numList = "123456789";
        String rev = "";
        int maxNumCount = 4;
        int length = 6;
        Random f = new Random();
        for (int i = 0; i < length; i++) {
            if (f.nextBoolean() && maxNumCount > 0) {
                maxNumCount--;
                rev += numList.charAt(f.nextInt(numList.length()));
            } else {
                rev += charList.charAt(f.nextInt(charList.length()));
            }
        }
        return rev;
    }


    public static String stringToHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            b[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return b;
    }


    public static void main(String[] args) throws IOException {


    }



    @Test
    public static String xxx() {
        String methodName = "queryNftSwap";
        String fromAddr = "0x1144a7317e7182cb42047a10d6c8aa65db5d935e";
        List<Type> inputParameters = new ArrayList<>();
//        List<TypeReference<?>> outputParameters = new ArrayList<>();
//        TypeReference<ERC721Asset> typeReference = new TypeReference<ERC721Asset>() {
//        };
//        outputParameters.add(typeReference);
       // List<TypeReference<?>> outputParameters = Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {});

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        TypeReference<?> typeReference = new TypeReference<DynamicArray<Uint>>() {
        };
        //outputParameters.add(typeReference);
        //outputParameters.add(new TypeReference.StaticArrayTypeReference<StaticArray<Uint256>>(3) {});
        //outputParameters.add(new TypeReference.StaticArrayTypeReference<DynamicArray<Uint256>>(3){});
        //outputParameters.add(new TypeReference<Uint>(){});
       // outputParameters.add(new TypeReference.StaticArrayTypeReference<DynamicStruct>());


        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);


        String value = new String("0x0000000000000000000000000000000000000000000000000000000000000037"
                + "0000000000000000000000000000000000000000000000000000000000000001"
                + "000000000000000000000000000000000000000000000000000000000000000a");
        List<Type> results = FunctionReturnDecoder.decode(value, function.getOutputParameters());

        DynamicArray type = (DynamicArray)FunctionReturnDecoder.decodeIndexedValue(value, new TypeReference<DynamicArray>() {
        });

        return results.get(0).toString();
    }

    public static class Foo extends DynamicStruct {
        public String id;

        public String name;

        public Foo(String id, String name) {
            super(
                    new org.web3j.abi.datatypes.Utf8String(id),
                    new org.web3j.abi.datatypes.Utf8String(name));
            this.id = id;
            this.name = name;
        }

        public Foo(Utf8String id, Utf8String name) {
            super(id, name);
            this.id = id.getValue();
            this.name = name.getValue();
        }
    }

    public static final org.web3j.abi.datatypes.Function getFooFunction =
            new org.web3j.abi.datatypes.Function(
                    "dff", Arrays.<Type>asList(), Arrays.asList(new TypeReference<Foo>() {}));
    public static void testDecodeDynamicStruct() {
        String rawInput =
                "0x0000000000000000000000000000000000000000000000000000000000000020"
                        + "0000000000000000000000000000000000000000000000000000000000000040"
                        + "0000000000000000000000000000000000000000000000000000000000000080"
                        + "0000000000000000000000000000000000000000000000000000000000000002"
                        + "6964000000000000000000000000000000000000000000000000000000000000"
                        + "0000000000000000000000000000000000000000000000000000000000000004"
                        + "6e616d6500000000000000000000000000000000000000000000000000000000";


        List<Type> decode = FunctionReturnDecoder.decode(
                rawInput, getFooFunction.getOutputParameters());
        Foo type = (Foo)decode.get(0);

        System.out.println("name:" + type.name+"value:" + type.id);
    }

    public static class Boz extends DynamicStruct {
        public BigInteger data;

        public String id;



        public Boz(Uint256 data, Utf8String id) {
            super(data, id);
            this.data = data.getValue();
            this.id = id.getValue();
        }
    }

    public static final org.web3j.abi.datatypes.Function getBozFunction =
            new org.web3j.abi.datatypes.Function(
                    "dfffe",
                    Collections.<Type>emptyList(),
                    Arrays.<TypeReference<?>>asList(new TypeReference<Boz>() {}));

    public static void testDecodeDynamicStruct2() {
        String rawInput =
                "0x0000000000000000000000000000000000000000000000000000000000000020"
                        + "0000000000000000000000000000000000000000000000000000000000000001"
                        + "0000000000000000000000000000000000000000000000000000000000000040"
                        + "0000000000000000000000000000000000000000000000000000000000000002"
                        + "6964000000000000000000000000000000000000000000000000000000000000";

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        TypeReference<Boz> typeReference = new TypeReference<Boz>() {
        };
        outputParameters.add(typeReference);
        Function dfffe = new Function(
                "dfffe",
                Collections.<Type>emptyList(),
                outputParameters);

        List<Type> decode = FunctionReturnDecoder.decode(
                        rawInput, dfffe.getOutputParameters());
        Boz type = (Boz)decode.get(0);

        System.out.println("data:" + type.data+"   value:" + type.id);

    }


    public static class ERC721Asset extends DynamicStruct {
        public String addr;
        public Uint256[] ids;

        public ERC721Asset(Address erc721Addr, DynamicArray<Uint256> ids) {
            super(erc721Addr, ids);
            this.addr = erc721Addr.getValue();
            this.ids = ids.getValue().toArray(new Uint256[0]);
        }
    }

    public static void testDecodeDynamicStruct3() {
        String rawInput =
                "0x0000000000000000000000000000000000000000000000000000000000000020000000000000000000000000bd893ae8eb937d71f853a65e1c6ecb93b846bc57000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000191";

        List<TypeReference<?>> outputParameters = new ArrayList<>();
        TypeReference<ERC721Asset> typeReference = new TypeReference<ERC721Asset>() {
        };
        outputParameters.add(new TypeReference<ERC721Asset>() {});
        Function dfffe = new Function(
                "dfffeg",
                Collections.<Type>emptyList(),
                outputParameters);

        List<Type> decode = FunctionReturnDecoder.decode(
                rawInput, dfffe.getOutputParameters());
        ERC721Asset type = (ERC721Asset)decode.get(0);


        System.out.println("data:" + type.addr);

    }
}
