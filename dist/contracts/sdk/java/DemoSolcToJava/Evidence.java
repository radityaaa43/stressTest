package DemoSolcToJava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionEncoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class Evidence extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b506040516107bf3803806107bf833981018060405281019080805182019291906020018051820192919060200180518201929190505050826000908051906020019061005d92919061020a565b50816001908051906020019061007492919061020a565b50806002908051906020019061008b92919061020a565b507faf1f4f8d84431b65de566a0a4f73763572c14edb25a1360312ff3c7b5386191183838360405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b838110156100f85780820151818401526020810190506100dd565b50505050905090810190601f1680156101255780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b8381101561015e578082015181840152602081019050610143565b50505050905090810190601f16801561018b5780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b838110156101c45780820151818401526020810190506101a9565b50505050905090810190601f1680156101f15780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a15050506102af565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061024b57805160ff1916838001178555610279565b82800160010185558215610279579182015b8281111561027857825182559160200191906001019061025d565b5b509050610286919061028a565b5090565b6102ac91905b808211156102a8576000816000905550600101610290565b5090565b90565b610501806102be6000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063596f21f814610051578063c7eaf9b4146101b9575b600080fd5b34801561005d57600080fd5b50610066610249565b60405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b838110156100ae578082015181840152602081019050610093565b50505050905090810190601f1680156100db5780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b838110156101145780820151818401526020810190506100f9565b50505050905090810190601f1680156101415780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b8381101561017a57808201518184015260208101905061015f565b50505050905090810190601f1680156101a75780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b3480156101c557600080fd5b506101ce610433565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561020e5780820151818401526020810190506101f3565b50505050905090810190601f16801561023b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6060806060600060016002828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156102e95780601f106102be576101008083540402835291602001916102e9565b820191906000526020600020905b8154815290600101906020018083116102cc57829003601f168201915b50505050509250818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103855780601f1061035a57610100808354040283529160200191610385565b820191906000526020600020905b81548152906001019060200180831161036857829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104215780601f106103f657610100808354040283529160200191610421565b820191906000526020600020905b81548152906001019060200180831161040457829003601f168201915b50505050509050925092509250909192565b606060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104cb5780601f106104a0576101008083540402835291602001916104cb565b820191906000526020600020905b8154815290600101906020018083116104ae57829003601f168201915b50505050509050905600a165627a7a72305820cb793324742077d9c7964f621fe8f3a7fca57302a4fcd1a2b846bf09c120dc6e0029"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b506040516107bf3803806107bf833981018060405281019080805182019291906020018051820192919060200180518201929190505050826000908051906020019061005d92919061020a565b50816001908051906020019061007492919061020a565b50806002908051906020019061008b92919061020a565b507f862dc616a336d85acf6c0a863ee6e67b03a19d22753b3455d915fddcdc414ea483838360405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b838110156100f85780820151818401526020810190506100dd565b50505050905090810190601f1680156101255780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b8381101561015e578082015181840152602081019050610143565b50505050905090810190601f16801561018b5780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b838110156101c45780820151818401526020810190506101a9565b50505050905090810190601f1680156101f15780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a15050506102af565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061024b57805160ff1916838001178555610279565b82800160010185558215610279579182015b8281111561027857825182559160200191906001019061025d565b5b509050610286919061028a565b5090565b6102ac91905b808211156102a8576000816000905550600101610290565b5090565b90565b610501806102be6000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680634ae70cef14610051578063995297f3146101b9575b600080fd5b34801561005d57600080fd5b50610066610249565b60405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b838110156100ae578082015181840152602081019050610093565b50505050905090810190601f1680156100db5780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b838110156101145780820151818401526020810190506100f9565b50505050905090810190601f1680156101415780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b8381101561017a57808201518184015260208101905061015f565b50505050905090810190601f1680156101a75780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b3480156101c557600080fd5b506101ce610433565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561020e5780820151818401526020810190506101f3565b50505050905090810190601f16801561023b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6060806060600060016002828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156102e95780601f106102be576101008083540402835291602001916102e9565b820191906000526020600020905b8154815290600101906020018083116102cc57829003601f168201915b50505050509250818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103855780601f1061035a57610100808354040283529160200191610385565b820191906000526020600020905b81548152906001019060200180831161036857829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104215780601f106103f657610100808354040283529160200191610421565b820191906000526020600020905b81548152906001019060200180831161040457829003601f168201915b50505050509050925092509250909192565b606060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104cb5780601f106104a0576101008083540402835291602001916104cb565b820191906000526020600020905b8154815290600101906020018083116104ae57829003601f168201915b50505050509050905600a165627a7a723058208af0a32982e2f92488c5451e22f42138508948faf64eaac2b64c969311c1d06b0029"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":true,\"inputs\":[],\"name\":\"getEvidence\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getEvidenceInfo\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"name\":\"evi\",\"type\":\"string\"},{\"name\":\"info\",\"type\":\"string\"},{\"name\":\"id\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"evi\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"info\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"id\",\"type\":\"string\"}],\"name\":\"newEvidenceEvent\",\"type\":\"event\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_GETEVIDENCE = "getEvidence";

    public static final String FUNC_GETEVIDENCEINFO = "getEvidenceInfo";

    public static final Event NEWEVIDENCEEVENT_EVENT = new Event("newEvidenceEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    protected Evidence(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public Tuple3<String, String, String> getEvidence() throws ContractException {
        final Function function = new Function(FUNC_GETEVIDENCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple3<String, String, String>(
                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue());
    }

    public String getEvidenceInfo() throws ContractException {
        final Function function = new Function(FUNC_GETEVIDENCEINFO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public List<NewEvidenceEventEventResponse> getNewEvidenceEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWEVIDENCEEVENT_EVENT, transactionReceipt);
        ArrayList<NewEvidenceEventEventResponse> responses = new ArrayList<NewEvidenceEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewEvidenceEventEventResponse typedResponse = new NewEvidenceEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.evi = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.info = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.id = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeNewEvidenceEventEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(NEWEVIDENCEEVENT_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeNewEvidenceEventEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(NEWEVIDENCEEVENT_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public static Evidence load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new Evidence(contractAddress, client, credential);
    }

    public static Evidence deploy(Client client, CryptoKeyPair credential, String evi, String info, String id) throws ContractException {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(evi), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(info), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(id)));
        return deploy(Evidence.class, client, credential, getBinary(client.getCryptoSuite()), encodedConstructor);
    }

    public static class NewEvidenceEventEventResponse {
        public TransactionReceipt.Logs log;

        public String evi;

        public String info;

        public String id;
    }
}
