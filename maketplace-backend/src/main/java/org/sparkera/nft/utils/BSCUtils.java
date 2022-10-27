package org.sparkera.nft.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.sparkera.nft.common.GameProcess;
import org.sparkera.nft.dao.dto.Erc20Dto;
import org.sparkera.nft.dao.entity.NftGameSwapInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
@Data
@Component
@Slf4j
public class BSCUtils {
    private static final String HEX_PREFIX="0x";
    public static String getNode_url() {
        return node_url;
    }
    @Value("${bsc.node.url}")
    public void setNode_url(String node_url) {
        BSCUtils.node_url = node_url;
    }

    public static String getWorld_url() { return world_url;}
    @Value("${game.world-url}")
    public void setWorld_url(String world_url) { BSCUtils.world_url = world_url;}

    public static String getSso_login_url() { return sso_login;}
    @Value("${sso.login}")
    public void setSso_login_url(String sso_login) {BSCUtils.sso_login = sso_login;}

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(1800, TimeUnit.SECONDS)
            .writeTimeout(1800, TimeUnit.SECONDS)
            .readTimeout(1800, TimeUnit.SECONDS)
            .build();

    public static Web3j getWeb3j() {
        if(web3j==null){
            web3j = Web3j.build(new HttpService(getNode_url(),client,false));
        }
        return web3j;
    }

    public static void setWeb3j(Web3j web3j) {
        BSCUtils.web3j = web3j;
    }

    private static Web3j web3j;
    private static String node_url;
    private static String world_url;
    private static String sso_login;
    private static String fireworkCardAddress;
    private static String NFTTransferAddress;
    private static String fireworkCardMint_topic;
    private static String fireworkCardMutated_topic;
    private static String Transfer_topic;
    private static String NFTtransfer_topic;
    private static String HSE_address;
    private static String PSE_address;
    private static String PSE_swap_address;
    private static String EGSE_address;
    private static String SHIP_address;
    private static String EGG_address;
    private static String MBSE_address;
    private static String MBSERebate_address;
    private static String ASE_address;
    private static String ISE_address;
    private static String PSEMint_topic;
    private static String PSESwap_topic;
    private static String EGSEMint_topic;
    private static String SHIPMint_topic;
    private static String EGGMint_topic;
    private static String ASEMint_topic;
    private static String ISE_topic;
    private static String Transfer1155_topic;
    private static String MBSERebate_topic;
    private static String CheckRebate_address;
    private static String NftTakeToGame_address;
    private static String NftTakeToGame_topic;

    private static String Bnb_address;
    private static String WBnb_address;
    private static String Quark_address;
    private static String Fire_address;
    private static String Usdt_address;

    private static Boolean SCAN;
    private static long CHAIN_ID;
    public static Boolean getSCAN() {
        return SCAN;
    }
    @Value("${bsc.scan}")
    public void setSCAN(Boolean SCAN) {
        BSCUtils.SCAN = SCAN;
    }

    public static long getChainId() {
        return CHAIN_ID;
    }
    @Value("${bsc.chain-id}")
    public void setChainId(long chainId) {
        BSCUtils.CHAIN_ID = chainId;
    }

    public static String getBnb_address() {
        return Bnb_address;
    }
    @Value("${erc20.bnb}")
    public void setBnb_address(String bnbAddress) {
        BSCUtils.Bnb_address = bnbAddress.toLowerCase();
    }

    public static String getWBnb_address() {
        return WBnb_address;
    }
    @Value("${erc20.wbnb}")
    public void setWBnb_address(String WBnbAddress) {
        BSCUtils.WBnb_address = HEX_PREFIX+WBnbAddress.toLowerCase();
    }

    public static String getQuark_address() {
        return Quark_address;
    }
    @Value("${erc20.quark}")
    public void setQuark_address(String quarkAddress) {
        BSCUtils.Quark_address = HEX_PREFIX+quarkAddress.toLowerCase();
    }

    public static String getFire_address() {
        return Fire_address;
    }
    @Value("${erc20.fire}")
    public void setFire_address(String fireAddress) {
        BSCUtils.Fire_address = HEX_PREFIX+fireAddress.toLowerCase();
    }

    public static String getUsdt_address() {
        return Usdt_address;
    }
    @Value("${erc20.usdt}")
    public void setUsdt_address(String usdtAddress) {
        BSCUtils.Usdt_address = HEX_PREFIX+usdtAddress.toLowerCase();
    }

    public static String getNFTtransfer_topic() {
        return NFTtransfer_topic;
    }
    @Value("${bsc.contract.NFTtransfer_topic}")
    public void setNFTtransfer_topic(String NFTtransfer_topic) {
        BSCUtils.NFTtransfer_topic = HEX_PREFIX+NFTtransfer_topic;
    }

    public static String getNFTQuickSale_topic() {
        return NFTQuickSale_topic;
    }
    @Value("${bsc.contract.NFTQuickSale_topic}")
    public void setNFTQuickSale_topic(String NFTQuickSale_topic) {
        BSCUtils.NFTQuickSale_topic = HEX_PREFIX+NFTQuickSale_topic;
    }

    private static String NFTQuickSale_topic;
    public static String getFireworkCardAddress() {
        return fireworkCardAddress;
    }
    @Value("${bsc.contract.fireworkcard_address}")
    public void setFireworkCardAddress(String fireworkCardAddress) {
        BSCUtils.fireworkCardAddress = HEX_PREFIX+fireworkCardAddress;
    }

    public static String getNFTTransferAddress() {
        return NFTTransferAddress;
    }
    @Value("${bsc.contract.NFTtransfer_address}")
    public void setNFTTransferAddress(String NFTTransferAddress) {
        BSCUtils.NFTTransferAddress = HEX_PREFIX+NFTTransferAddress;
    }

    public static String getFireworkCardMint_topic() {
        return fireworkCardMint_topic;
    }
    @Value("${bsc.contract.fireworkCardMint_topic}")
    public void setFireworkCardMint_topic(String fireworkCardMint_topic) {
        BSCUtils.fireworkCardMint_topic = HEX_PREFIX+fireworkCardMint_topic;
    }

    public static String getFireworkCardMutated_topic() {
        return fireworkCardMutated_topic;
    }
    @Value("${bsc.contract.fireworkCardMutated_topic}")
    public void setFireworkCardMutated_topic(String fireworkCardMutated_topic) {
        BSCUtils.fireworkCardMutated_topic = HEX_PREFIX+fireworkCardMutated_topic;
    }

    public static String getTransfer_topic() {
        return Transfer_topic;
    }
    @Value("${bsc.contract.Transfer_topic}")
    public void setTransfer_topic(String transfer_topic) {
        BSCUtils.Transfer_topic = HEX_PREFIX+transfer_topic;
    }

    public static String getHSE_address() {
        return HSE_address;
    }
    @Value("${bsc.contract.HSE_address}")
    public void setHSE_address(String HSE_address) {
        BSCUtils.HSE_address = HEX_PREFIX+HSE_address;
    }

    public static String getPSE_address() {
        return PSE_address;
    }
    @Value("${bsc.contract.PSE_address}")
    public void setPSE_address(String PSE_address) {
        BSCUtils.PSE_address = HEX_PREFIX+PSE_address;
    }

    public static String getPSE_swap_address() {
        return PSE_swap_address;
    }
    @Value("${bsc.contract.PSE_swap_address}")
    public void setPSE_swap_address(String PSE_swap_address) {
        BSCUtils.PSE_swap_address = HEX_PREFIX+PSE_swap_address;
    }

    public static String getCheckRebate_address() {
        return CheckRebate_address;
    }
    @Value("${bsc.contract.CheckRebate_address}")
    public void setCheckRebate_address(String checkRebate_address) {
        BSCUtils.CheckRebate_address = HEX_PREFIX+checkRebate_address;
    }

    public static String getNftTakeToGame_adress() {
        return NftTakeToGame_address;
    }
    @Value("${bsc.contract.NftTakeToGame_address}")
    public void setNftTakeToGame_address(String nftTakeToGame_address){
        BSCUtils.NftTakeToGame_address = HEX_PREFIX+nftTakeToGame_address;
    }

    public static String getNftTakeToGame_topic() {
        return NftTakeToGame_topic;
    }
    @Value("${bsc.contract.NftTakeToGame_topic}")
    public void setNftTakeToGame_topic(String nftTakeToGame_topic){
        BSCUtils.NftTakeToGame_topic = HEX_PREFIX+nftTakeToGame_topic;
    }

    public static String getEGSE_address() {
        return EGSE_address;
    }
    @Value("${bsc.contract.EGSE_address}")
    public void setEGSE_address(String EGSE_address) {
        BSCUtils.EGSE_address = HEX_PREFIX+EGSE_address;
    }

    public static String getSHIP_address() {
        return SHIP_address;
    }
    @Value("${bsc.contract.SHIP_address}")
    public void setSHIP_address(String SHIP_address) {
        BSCUtils.SHIP_address = HEX_PREFIX+SHIP_address;
    }

    public static String getEGG_address() {
        return EGG_address;
    }
    @Value("${bsc.contract.EGG_address}")
    public void setEGG_address(String EGG_address) {
        BSCUtils.EGG_address = HEX_PREFIX+EGG_address;
    }

    public static String getMBSE_address() {
        return MBSE_address;
    }
    @Value("${bsc.contract.MBSE_address}")
    public void setMBSE_address(String MBSE_address) {
        BSCUtils.MBSE_address = HEX_PREFIX+MBSE_address;
    }

    public static String getMBSERebate_address() {
        return MBSERebate_address;
    }

    @Value("${bsc.contract.MBSERebate_address}")
    public void SetMBSERebate_address(String MBSERebate_address) {
        BSCUtils.MBSERebate_address = HEX_PREFIX+MBSERebate_address;
    }

    public static String getASE_address() {
        return ASE_address;
    }
    @Value("${bsc.contract.ASE_address}")
    public void setASE_address(String ASE_address) {
        BSCUtils.ASE_address = HEX_PREFIX+ASE_address;
    }

    public static String getISE_address() {
        return ISE_address;
    }
    @Value("${bsc.contract.ISE_address}")
    public void setISE_address(String ISE_address) {
        BSCUtils.ISE_address = HEX_PREFIX+ISE_address;
    }

    public static String getPSEMint_topic() {
        return PSEMint_topic;
    }
    @Value("${bsc.contract.PSEMint_topic}")
    public void setPSEMint_topic(String PSEMint_topic) {
        BSCUtils.PSEMint_topic = HEX_PREFIX+PSEMint_topic;
    }

    public static String getPSESwap_topic() {
        return PSESwap_topic;
    }
    @Value("${bsc.contract.PSE_swap_topic}")
    public void setPSESwap_topic(String PSESwap_topic) {
        BSCUtils.PSESwap_topic = HEX_PREFIX+PSESwap_topic;
    }

    public static String getEGSEMint_topic() {
        return EGSEMint_topic;
    }
    @Value("${bsc.contract.EGSEMint_topic}")
    public void setEGSEMint_topic(String EGSEMint_topic) {
        BSCUtils.EGSEMint_topic = HEX_PREFIX+EGSEMint_topic;
    }

    public static String getSHIPMint_topic() {
        return SHIPMint_topic;
    }
    @Value("${bsc.contract.SHIPMint_topic}")
    public void setSHIPMint_topic(String SHIPMint_topic) {
        BSCUtils.SHIPMint_topic = HEX_PREFIX+SHIPMint_topic;
    }

    public static String getEGGMint_topic() {
        return EGGMint_topic;
    }
    @Value("${bsc.contract.EGGMint_topic}")
    public void setEGGMint_topic(String EGGMint_topic) {
        BSCUtils.EGGMint_topic = HEX_PREFIX+EGGMint_topic;
    }

    public static String getASEMint_topic() {
        return ASEMint_topic;
    }
    @Value("${bsc.contract.ASEMint_topic}")
    public void setASEMint_topic(String ASEMint_topic) {
        BSCUtils.ASEMint_topic = HEX_PREFIX+ASEMint_topic;
    }

    public static String getISE_topic() {
        return ISE_topic;
    }
    @Value("${bsc.contract.ISE_topic}")
    public void setISE_topic(String ISE_topic) {
        BSCUtils.ISE_topic = HEX_PREFIX+ISE_topic;
    }

    public static String getTransfer1155_topic() {
        return Transfer1155_topic;
    }
    @Value("${bsc.contract.Transfer1155_topic}")
    public void setTransfer1155_topic(String Transfer1155_topic) { BSCUtils.Transfer1155_topic = HEX_PREFIX+Transfer1155_topic; }

    public static String getMBSERebate_topic() { return MBSERebate_topic;}
    @Value("${bsc.contract.MBSERebate_topic}")
    public void setMBSERebate_topic(String MBSERebate_topic) { BSCUtils.MBSERebate_topic = HEX_PREFIX+MBSERebate_topic;}

    public static final String PERSONAL_MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";
    public static boolean validateSignature(String signature, String message, String address){
        //参考 eth_sign in https://github.com/ethereum/wiki/wiki/JSON-RPC
        // eth_sign
        // The sign method calculates an Ethereum specific signature with:
        //    sign(keccak256("\x19Ethereum Signed Message:\n" + len(message) + message))).
        //
        // By adding a prefix to the message makes the calculated signature recognisable as an Ethereum specific signature.
        // This prevents misuse where a malicious DApp can sign arbitrary data (e.g. transaction) and use the signature to
        // impersonate the victim.

        String prefix = PERSONAL_MESSAGE_PREFIX + message.length();
        byte[] msgHash = Hash.sha3((prefix + message).getBytes());

        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        if (v < 27) {
            v += 27;
        }

        Sign.SignatureData sd = new Sign.SignatureData(
                v,
                Arrays.copyOfRange(signatureBytes, 0, 32),
                Arrays.copyOfRange(signatureBytes, 32, 64));

        String addressRecovered = null;
        boolean match = false;
        // Iterate for each possible key to recover
        for (int i = 0; i < 4; i++) {
            BigInteger publicKey = Sign.recoverFromSignature(
                    (byte) i,
                    new ECDSASignature(new BigInteger(1, sd.getR()), new BigInteger(1, sd.getS())),
                    msgHash);

            if (publicKey != null) {
                addressRecovered = "0x" + Keys.getAddress(publicKey);
                if (addressRecovered.equals(address)) {
                    match = true;
                    break;
                }
            }
        }
        return match;

    }
    public static  BigInteger getBlockNumber(){
        BigInteger rs = new BigInteger("0");
        try {
            rs = getWeb3j().ethBlockNumber().send().getBlockNumber();

        }catch (IOException e){
            System.out.println(e);
        }
        return rs;
    }
    public static BigInteger getNonce(String from) {
        EthGetTransactionCount transactionCount;
        BigInteger nonce = null;
        try {
            transactionCount = getWeb3j().ethGetTransactionCount(from, DefaultBlockParameterName.LATEST).sendAsync().get();
            nonce = transactionCount.getTransactionCount();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Web3j --> ethGetTransactionCount:" + e.getCause().getMessage());
        }
        return nonce;
    }
    public static String tradeTransferNft(String contractAddress,BigInteger tokenId,String fromAddress, String ownerPrivateKey,String toAddress ,
                                          String erc20Address,BigDecimal price,BigDecimal txFee,int _gasPrice, int _gasLimit,int decimals){
        try {
            Credentials credentials = Credentials.create(ownerPrivateKey);

            EthGetTransactionCount ethGetTransactionCount = getWeb3j().ethGetTransactionCount(
                    credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
            Function function = new Function(
                    "transferNft",
                    Arrays.asList(new Address(contractAddress),
                            new Uint256(tokenId),
                            new Address(fromAddress),
                            new Address(toAddress),
                            new Address(erc20Address),
                            new Uint256(Convert.toWei(price,W3ToWeiUtil.getUnit(decimals)).toBigInteger()),
                            new Uint256(Convert.toWei(txFee,W3ToWeiUtil.getUnit(decimals)).toBigInteger())),
                    Arrays.asList(new TypeReference<Type>() {
                    }));
            BigInteger gasPrice = Convert.toWei(String.valueOf(_gasPrice), Convert.Unit.GWEI).toBigInteger();
            String encodedFunction = FunctionEncoder.encode(function);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice,
                    Convert.toWei(String.valueOf(_gasLimit), Convert.Unit.WEI).toBigInteger(), NFTTransferAddress, encodedFunction);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, BSCUtils.getChainId(), credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = getWeb3j().ethSendRawTransaction(hexValue).sendAsync().get();
            if(ethSendTransaction.hasError()){
                return "error-"+ethSendTransaction.getError().getMessage();
            }
            String transactionHash = ethSendTransaction.getTransactionHash();
            return transactionHash;
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    public static String tradeTransferNftMul(String contractAddress,Integer id, Integer cnt, String fromAddress, String ownerPrivateKey,String toAddress ,
                                          String erc20Address,BigDecimal price,BigDecimal txFee,int _gasPrice, int _gasLimit,int decimals){
        try {
            Credentials credentials = Credentials.create(ownerPrivateKey);

            EthGetTransactionCount ethGetTransactionCount = getWeb3j().ethGetTransactionCount(
                    credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();

            Function function = new Function(
                    "transferItem1155",
                    Arrays.asList(new Address(contractAddress),
                            new Address(fromAddress),
                            new Address(toAddress),
                            new DynamicArray(new Uint256(id)),
                            new DynamicArray(new Uint256(cnt)),
                            new Address(erc20Address),
                            new Uint256(Convert.toWei(price,W3ToWeiUtil.getUnit(decimals)).toBigInteger()),
                            new Uint256(Convert.toWei(txFee,W3ToWeiUtil.getUnit(decimals)).toBigInteger())),
                    Arrays.asList(new TypeReference<Type>() {
                    }));
            BigInteger gasPrice = Convert.toWei(String.valueOf(_gasPrice), Convert.Unit.GWEI).toBigInteger();
            String encodedFunction = FunctionEncoder.encode(function);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice,
                    Convert.toWei(String.valueOf(_gasLimit), Convert.Unit.WEI).toBigInteger(), NFTTransferAddress, encodedFunction);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, BSCUtils.getChainId(), credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = getWeb3j().ethSendRawTransaction(hexValue).sendAsync().get();
            if(ethSendTransaction.hasError()){
                return "error-"+ethSendTransaction.getError().getMessage();
            }
            String transactionHash = ethSendTransaction.getTransactionHash();
            return transactionHash;
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    public static boolean checkWithDrawDepo(String checkAddr, String ownerAddress, BigInteger transId) throws ExecutionException, InterruptedException {
        Function function = new Function(
                "queryClaim",
                Arrays.asList(new Address(ownerAddress),
                        new Uint256(transId)),
                Arrays.asList(new TypeReference<Uint256>() {}));
        String encodedFunction = FunctionEncoder.encode(function);
        EthCall response = getWeb3j().ethCall(
                Transaction.createEthCallTransaction(ownerAddress, checkAddr, encodedFunction),
                DefaultBlockParameterName.LATEST).sendAsync().get();

        String value = response.getValue();

        List<Type> someTypes = FunctionReturnDecoder.decode(
                response.getValue(), function.getOutputParameters());
        Uint256 type = (Uint256)someTypes.get(0);
        if (type.getValue().intValue() == 0) {
            return false;
        }
        return true;
    }

    public static String quickSaleNft(String contractAddress,BigInteger tokenId,String fromAddress, String ownerPrivateKey,String toAddress ,
                                          String erc20Address,BigDecimal price,int _gasPrice, int _gasLimit,int decimals){
        try {
            Credentials credentials = Credentials.create(ownerPrivateKey);

            EthGetTransactionCount ethGetTransactionCount = getWeb3j().ethGetTransactionCount(
                    credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
            Function function = new Function(
                    "quickSale",
                    Arrays.asList(new Address(contractAddress),
                            new Uint256(tokenId),
                            new Address(fromAddress),
                            new Address(toAddress),
                            new Address(erc20Address),
                            new Uint256(Convert.toWei(price,W3ToWeiUtil.getUnit(decimals)).toBigInteger())),
                        Arrays.asList(new TypeReference<Type>() {
                    }));
            BigInteger gasPrice = Convert.toWei(String.valueOf(_gasPrice), Convert.Unit.GWEI).toBigInteger();
            String encodedFunction = FunctionEncoder.encode(function);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice,
                    Convert.toWei(String.valueOf(_gasLimit), Convert.Unit.WEI).toBigInteger(), NFTTransferAddress, encodedFunction);
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, BSCUtils.getChainId(), credentials);
            String hexValue = Numeric.toHexString(signedMessage);
            EthSendTransaction ethSendTransaction = getWeb3j().ethSendRawTransaction(hexValue).sendAsync().get();
            if(ethSendTransaction.hasError()){
                return "error-"+ethSendTransaction.getError().getMessage();
            }
            String transactionHash = ethSendTransaction.getTransactionHash();
            return transactionHash;
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }


    public static void getAllitemDataByAddress(String userAddress){
        String methodName = "getAllitemDataByAddress";
        //String methodName = "getAllTokenIdByAddress";
        String fromAddr = "0x1144a7317e7182cb42047a10d6c8aa65db5d935e";
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Address(userAddress));
        List<TypeReference<?>> outputParameters = new ArrayList<>();
        TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(fromAddr, "0xbaE875E56E8b8Bd0b8fbe3e44BF7889fd09A1AeE", data);
        EthCall ethCall;
        try {
            ethCall = getWeb3j().ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            System.out.println(ethCall.getValue());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    public static String getTokenURI(String contractAddress,BigInteger tokenId){
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
        Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);
        EthCall ethCall;
        try {
            ethCall = getWeb3j().ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            return results.get(0).toString();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Erc20Dto getAllErc20() {
        Erc20Dto dto = new Erc20Dto();
        dto.setBnb(getBnb_address());
        dto.setWbnb(getWBnb_address());
        dto.setFire(getFire_address());
        dto.setUsdt(getUsdt_address());
        dto.setQuark(getQuark_address());
        return dto;
    }

    public static NftGameSwapInfo queryNftSwap(String account, Integer toGame, BigInteger hashId) {
        log.info("account:{} fromGame:{} hashId:{}", account, toGame, hashId);
        String methodName = "queryNftSwap";
        String fromAddr = "0x1144a7317e7182cb42047a10d6c8aa65db5d935e";
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Address(account));
        inputParameters.add(new Uint8(toGame));
        inputParameters.add(new Uint64(hashId));
        List<TypeReference<?>> outputParameters = new ArrayList<>();
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);

        Transaction transaction = Transaction.createEthCallTransaction(fromAddr, getNftTakeToGame_adress(), data);
        EthCall ethCall;
        NftGameSwapInfo retInfo = new NftGameSwapInfo();
        try {
            List<BigInteger> listTokenId = new ArrayList<BigInteger>();

            ethCall = getWeb3j().ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            String value = ethCall.getValue();
            String nftAddress = "0x" + value.substring(90, 130);
            Integer cnt = Integer.valueOf(value.substring(195, 258).replaceAll("^(0+)", ""),16);
            for (int i = 0; i < cnt; i++) {
                String strTokenId = value.substring(258+1+i*64, 258+(i+1)*64).replaceAll("^(0+)", "");
                BigInteger tokenId = new BigInteger(strTokenId, 16);
                listTokenId.add(tokenId);
            }
            retInfo.setNftAddress(nftAddress);
            retInfo.setListTokenId(listTokenId);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retInfo;
    }

    public static String getSwapAsset(String contractAddress, BigInteger swapTransId) {
        String methodName = "tokenURI";
        String fromAddr = "0x1144a7317e7182cb42047a10d6c8aa65db5d935e";
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Uint256(swapTransId));
        List<TypeReference<?>> outputParameters = new ArrayList<>();
        TypeReference<Utf8String> typeReference = new TypeReference<Utf8String>() {
        };
        outputParameters.add(typeReference);
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(fromAddr, contractAddress, data);
        EthCall ethCall;
        try {
            ethCall = getWeb3j().ethCall(transaction, DefaultBlockParameterName.LATEST).sendAsync().get();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            return results.get(0).toString();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getGameItemURI(Integer id) {
        return "https://api.firework.games/gameitem/" + id;
    }
}
