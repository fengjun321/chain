package org.sparkera.nft.utils;

import okhttp3.OkHttpClient;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class test {
    private static Web3j web3j;
    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(1800, TimeUnit.SECONDS)
            .writeTimeout(1800, TimeUnit.SECONDS)
            .readTimeout(1800, TimeUnit.SECONDS)
            .build();
    public static void main(String[] args) {
        web3j = Web3j.build(new HttpService("https://data-seed-prebsc-1-s1.binance.org:8545",client,false));
        System.out.println(getTokenURI(new BigInteger("1")));
    }
    public static String getTokenURI(BigInteger tokenId){
        String methodName = "tokenURI";
        String fromAddr = "0x1144a7317e7182cb42047a10d6c8aa65db5d935e";
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Uint256(tokenId));
        List<TypeReference<?>> outputParameters = new ArrayList<>();
        TypeReference<Utf8String> typeReference = new TypeReference<Utf8String>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(fromAddr, "0xb0B0B5541ED23022A8d517bFC43e8BaC81ee5cA5", data);
        EthCall ethCall;
        try {
            ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            return results.get(0).toString();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
}
