package org.fisco.bcos.sdk.demo.transaction;

import java.math.BigInteger;
import java.util.List;
import org.fisco.bcos.sdk.abi.ABICodec;
import org.fisco.bcos.sdk.abi.ABICodecException;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.signature.SignatureResult;
import org.fisco.bcos.sdk.transaction.builder.TransactionBuilderInterface;
import org.fisco.bcos.sdk.transaction.builder.TransactionBuilderService;
import org.fisco.bcos.sdk.transaction.codec.encode.TransactionEncoderService;
import org.fisco.bcos.sdk.transaction.model.gas.DefaultGasProvider;
import org.fisco.bcos.sdk.transaction.model.po.RawTransaction;

public class BasicAbiTransaction {
    String contractName;
    String abiContent;
    String binContent;
    boolean isDeployTransaction = false;

    public boolean isDeployTransaction() {
        return isDeployTransaction;
    }

    public BasicAbiTransaction setDeployTransaction(boolean isDeployTransactoin) {
        this.isDeployTransaction = isDeployTransactoin;
        return this;
    }

    public String getBinContent() {
        return binContent;
    }

    public BasicAbiTransaction setBinContent(String binContent) {
        this.binContent = binContent;
        return this;
    }

    String methodName;
    String to;
    List<Object> params;

    public String getContractName() {
        return contractName;
    }

    public BasicAbiTransaction setContractName(String contractName) {
        this.contractName = contractName;
        return this;
    }

    BigInteger gasPrice = DefaultGasProvider.GAS_PRICE;
    BigInteger gasLimit = DefaultGasProvider.GAS_LIMIT;
    BigInteger value = BigInteger.ZERO;
    String extraData = "";

    CryptoSuite cryptoSuite;
    ABICodec abiCodec;
    TransactionEncoderService transactionEncoder;

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(BigInteger gasPrice) {
        this.gasPrice = gasPrice;
    }

    public BigInteger getGasLimit() {
        return gasLimit;
    }

    public BasicAbiTransaction setGasLimit(BigInteger gasLimit) {
        this.gasLimit = gasLimit;
        return this;
    }

    public BigInteger getValue() {
        return value;
    }

    public BasicAbiTransaction setValue(BigInteger value) {
        this.value = value;
        return this;
    }

    public String getExtraData() {
        return extraData;
    }

    public BasicAbiTransaction setExtraData(String extraData) {
        this.extraData = extraData;
        return this;
    }

    public BasicAbiTransaction() {}

    public BasicAbiTransaction(
            String ContractName_,
            String abiContent_,
            String methodName_,
            String to_,
            List<Object> params_) {

        contractName = ContractName_;
        abiContent = abiContent_;
        methodName = methodName_;
        to = to_;
        params = params_;
    }

    public BasicAbiTransaction setTools(
            CryptoSuite cryptoSuite_,
            ABICodec abiCodec_,
            TransactionEncoderService transactionEncoder_) {
        this.cryptoSuite = cryptoSuite_;
        this.abiCodec = abiCodec_;
        if (this.abiCodec == null) {
            this.abiCodec = new ABICodec(cryptoSuite);
        }
        this.transactionEncoder = transactionEncoder_;
        if (this.transactionEncoder == null) {
            this.transactionEncoder = new TransactionEncoderService(cryptoSuite);
        }
        return this;
    }

    public String getAbiContent() {
        return abiContent;
    }

    public BasicAbiTransaction setAbiContent(String abiContent) {
        this.abiContent = abiContent;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public BasicAbiTransaction setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public String getTo() {
        return to;
    }

    public BasicAbiTransaction setTo(String to) {
        this.to = to;
        return this;
    }

    public List<Object> getParams() {
        return params;
    }

    public BasicAbiTransaction setParams(List<Object> params) {
        this.params = params;
        return this;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param abiCodec ???????????????????????????ABICodeC??????
     * @param abiContent ?????????abi?????????(???????????????1????????????abi????????????)
     * @param binContent binary?????????(???????????????1????????????bin????????????)
     * @param params ????????????????????????????????????
     * @return ????????????????????????
     * @throws ABICodecException
     */
    public String encodeConstructor() throws ABICodecException {
        return abiCodec.encodeConstructor(abiContent, binContent, params);
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param abiCodec ???????????????????????????ABICodeC??????
     * @param abiContent ?????????abi?????????(???????????????1????????????abi????????????)
     * @param methodName ??????????????????????????????
     * @return ????????????????????????
     * @throws ABICodecException
     */
    public String encodeMethodInput(ABICodec abiCodec) throws ABICodecException {
        return abiCodec.encodeMethod(this.getAbiContent(), this.getMethodName(), this.getParams());
    }

    /**
     * ???RawTransaction??????RLP????????????????????????????????????
     *
     * @param rawTransaction ?????????????????????
     * @return ????????????????????????
     */
    public byte[] calcRawTransactionHash(RawTransaction rawTransaction) {
        byte[] encodedTransaction = transactionEncoder.encode(rawTransaction, null);
        return cryptoSuite.hash(encodedTransaction);
    }

    /**
     * ??????RawTransaction??????????????????????????????????????????
     *
     * @param transaction ????????????????????????
     * @param signatureResult ???????????????????????????????????????????????????
     * @return ???????????????????????????
     */
    public byte[] encodeRawTransactionWithSignature(
            RawTransaction transaction, SignatureResult signatureResult) {

        return transactionEncoder.encode(transaction, signatureResult);
    }

    public RawTransaction makeRawTransaction(Client client, int chainId, int groupId)
            throws ABICodecException {
        if (this.isDeployTransaction) return makeDeployRawTransaction(client, chainId, groupId);
        return makeMethodRawTransaction(client, chainId, groupId);
    }

    public RawTransaction makeRawTransactionByInput(
            Client client, int chainId, int groupId, String input) {
        // ??????TransactionBuilder?????????RawTransaction
        TransactionBuilderInterface transactionBuilder = new TransactionBuilderService(client);
        RawTransaction rawtx =
                transactionBuilder.createTransaction(
                        gasPrice,
                        gasLimit,
                        to,
                        input,
                        value,
                        BigInteger.valueOf(chainId),
                        BigInteger.valueOf(groupId),
                        extraData);
        return rawtx;
    }

    public RawTransaction makeDeployRawTransaction(Client client, int chainId, int groupId)
            throws ABICodecException {
        String deployInput = encodeConstructor();
        System.out.println("deploy contract bin:" + deployInput);
        System.out.println("bin size " + deployInput.length());
        // ??????TransactionBuilder?????????RawTransaction
        return makeRawTransactionByInput(client, chainId, groupId, deployInput);
    }

    // ?????????????????????
    public RawTransaction makeMethodRawTransaction(Client client, int chainId, int groupId)
            throws ABICodecException {

        // ??????????????????
        String txInput = encodeMethodInput(abiCodec);

        // ??????TransactionBuilder?????????RawTransaction
        return makeRawTransactionByInput(client, chainId, groupId, txInput);
    }
}
