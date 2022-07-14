package DemoSolcToJava;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint256;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class ParallelContract extends Contract {
    public static final String[] BINARY_ARRAY = {};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"functionName\",\"type\":\"string\"},{\"name\":\"criticalSize\",\"type\":\"uint256\"}],\"name\":\"registerParallelFunction\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"functionName\",\"type\":\"string\"}],\"name\":\"unregisterParallelFunction\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"enableParallel\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"disableParallel\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_REGISTERPARALLELFUNCTION = "registerParallelFunction";

    public static final String FUNC_UNREGISTERPARALLELFUNCTION = "unregisterParallelFunction";

    public static final String FUNC_ENABLEPARALLEL = "enableParallel";

    public static final String FUNC_DISABLEPARALLEL = "disableParallel";

    protected ParallelContract(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt registerParallelFunction(String functionName, String criticalSize) {
        final Function function = new Function(
                FUNC_REGISTERPARALLELFUNCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(functionName), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(criticalSize)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] registerParallelFunction(String functionName, String criticalSize, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REGISTERPARALLELFUNCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(functionName), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(criticalSize)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForRegisterParallelFunction(String functionName, String criticalSize) {
        final Function function = new Function(
                FUNC_REGISTERPARALLELFUNCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(functionName), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Uint256(criticalSize)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, BigInteger> getRegisterParallelFunctionInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REGISTERPARALLELFUNCTION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue()
                );
    }

    public TransactionReceipt unregisterParallelFunction(String functionName) {
        final Function function = new Function(
                FUNC_UNREGISTERPARALLELFUNCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(functionName)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] unregisterParallelFunction(String functionName, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_UNREGISTERPARALLELFUNCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(functionName)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForUnregisterParallelFunction(String functionName) {
        final Function function = new Function(
                FUNC_UNREGISTERPARALLELFUNCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(functionName)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getUnregisterParallelFunctionInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_UNREGISTERPARALLELFUNCTION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public TransactionReceipt enableParallel() {
        final Function function = new Function(
                FUNC_ENABLEPARALLEL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] enableParallel(TransactionCallback callback) {
        final Function function = new Function(
                FUNC_ENABLEPARALLEL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForEnableParallel() {
        final Function function = new Function(
                FUNC_ENABLEPARALLEL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public TransactionReceipt disableParallel() {
        final Function function = new Function(
                FUNC_DISABLEPARALLEL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] disableParallel(TransactionCallback callback) {
        final Function function = new Function(
                FUNC_DISABLEPARALLEL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForDisableParallel() {
        final Function function = new Function(
                FUNC_DISABLEPARALLEL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public static ParallelContract load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new ParallelContract(contractAddress, client, credential);
    }

    public static ParallelContract deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(ParallelContract.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }
}
