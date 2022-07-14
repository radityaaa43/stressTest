package DemoSolcToJava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Bool;
import org.fisco.bcos.sdk.abi.datatypes.DynamicBytes;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple8;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class SM2EvidenceVerify extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b506150066000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550610fe6806100626000396000f30060806040526004361062000043576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063d7f8df151462000048575b600080fd5b3480156200005557600080fd5b506200007460048036036200006e9190810190620003cb565b6200008d565b60405162000084929190620005ba565b60405180910390f35b60008060008060008c8c8c620000a262000265565b620000b0939291906200063b565b604051809103906000f080158015620000cd573d6000803e3d6000fd5b5092507f8b94c7f6b3fadc764673ea85b4bfef3e17ce928d13e51b818ddfa891ad0f1fcc836040516200010191906200059d565b60405180910390a1600090506200011b8989898962000182565b80935081925050508973ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff161415156200015e57600080fd5b600115158115151415156200017257600080fd5b5050509850989650505050505050565b6000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663cbdb3a67878787876040518563ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401620002039493929190620005e7565b6040805180830381600087803b1580156200021d57600080fd5b505af115801562000232573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506200025891908101906200038a565b9150915094509492505050565b6040516107bf80620007ee83390190565b600062000284823562000761565b905092915050565b60006200029a825162000761565b905092915050565b6000620002b0825162000781565b905092915050565b6000620002c682356200078d565b905092915050565b600082601f8301121515620002e257600080fd5b8135620002f9620002f382620006bb565b6200068d565b915080825260208301602083018583830111156200031657600080fd5b6200032383828462000797565b50505092915050565b600082601f83011215156200034057600080fd5b8135620003576200035182620006e8565b6200068d565b915080825260208301602083018583830111156200037457600080fd5b6200038183828462000797565b50505092915050565b600080604083850312156200039e57600080fd5b6000620003ae85828601620002a2565b9250506020620003c1858286016200028c565b9150509250929050565b600080600080600080600080610100898b031215620003e957600080fd5b600089013567ffffffffffffffff8111156200040457600080fd5b620004128b828c016200032c565b985050602089013567ffffffffffffffff8111156200043057600080fd5b6200043e8b828c016200032c565b975050604089013567ffffffffffffffff8111156200045c57600080fd5b6200046a8b828c016200032c565b96505060606200047d8b828c0162000276565b9550506080620004908b828c01620002b8565b94505060a089013567ffffffffffffffff811115620004ae57600080fd5b620004bc8b828c01620002ce565b93505060c0620004cf8b828c01620002b8565b92505060e0620004e28b828c01620002b8565b9150509295985092959890939650565b620004fd816200072b565b82525050565b6200050e816200074b565b82525050565b6200051f8162000757565b82525050565b6000620005328262000715565b80845262000548816020860160208601620007a6565b6200055381620007dc565b602085010191505092915050565b60006200056e8262000720565b80845262000584816020860160208601620007a6565b6200058f81620007dc565b602085010191505092915050565b6000602082019050620005b46000830184620004f2565b92915050565b6000604082019050620005d1600083018562000503565b620005e06020830184620004f2565b9392505050565b6000608082019050620005fe600083018762000514565b818103602083015262000612818662000525565b905062000623604083018562000514565b62000632606083018462000514565b95945050505050565b6000606082019050818103600083015262000657818662000561565b905081810360208301526200066d818562000561565b9050818103604083015262000683818462000561565b9050949350505050565b6000604051905081810181811067ffffffffffffffff82111715620006b157600080fd5b8060405250919050565b600067ffffffffffffffff821115620006d357600080fd5b601f19601f8301169050602081019050919050565b600067ffffffffffffffff8211156200070057600080fd5b601f19601f8301169050602081019050919050565b600081519050919050565b600081519050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60008115159050919050565b6000819050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60008115159050919050565b6000819050919050565b82818337600083830152505050565b60005b83811015620007c6578082015181840152602081019050620007a9565b83811115620007d6576000848401525b50505050565b6000601f19601f83011690509190505600608060405234801561001057600080fd5b506040516107bf3803806107bf833981018060405281019080805182019291906020018051820192919060200180518201929190505050826000908051906020019061005d92919061020a565b50816001908051906020019061007492919061020a565b50806002908051906020019061008b92919061020a565b507faf1f4f8d84431b65de566a0a4f73763572c14edb25a1360312ff3c7b5386191183838360405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b838110156100f85780820151818401526020810190506100dd565b50505050905090810190601f1680156101255780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b8381101561015e578082015181840152602081019050610143565b50505050905090810190601f16801561018b5780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b838110156101c45780820151818401526020810190506101a9565b50505050905090810190601f1680156101f15780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a15050506102af565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061024b57805160ff1916838001178555610279565b82800160010185558215610279579182015b8281111561027857825182559160200191906001019061025d565b5b509050610286919061028a565b5090565b6102ac91905b808211156102a8576000816000905550600101610290565b5090565b90565b610501806102be6000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063596f21f814610051578063c7eaf9b4146101b9575b600080fd5b34801561005d57600080fd5b50610066610249565b60405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b838110156100ae578082015181840152602081019050610093565b50505050905090810190601f1680156100db5780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b838110156101145780820151818401526020810190506100f9565b50505050905090810190601f1680156101415780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b8381101561017a57808201518184015260208101905061015f565b50505050905090810190601f1680156101a75780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b3480156101c557600080fd5b506101ce610433565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561020e5780820151818401526020810190506101f3565b50505050905090810190601f16801561023b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6060806060600060016002828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156102e95780601f106102be576101008083540402835291602001916102e9565b820191906000526020600020905b8154815290600101906020018083116102cc57829003601f168201915b50505050509250818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103855780601f1061035a57610100808354040283529160200191610385565b820191906000526020600020905b81548152906001019060200180831161036857829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104215780601f106103f657610100808354040283529160200191610421565b820191906000526020600020905b81548152906001019060200180831161040457829003601f168201915b50505050509050925092509250909192565b606060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104cb5780601f106104a0576101008083540402835291602001916104cb565b820191906000526020600020905b8154815290600101906020018083116104ae57829003601f168201915b50505050509050905600a165627a7a72305820cb793324742077d9c7964f621fe8f3a7fca573","02a4fcd1a2b846bf09c120dc6e0029a265627a7a72305820ccb723e5043f3fa61ae02fbbd4bbf2cdf29f72954ec5a4cbc0950b9eb6c3df726c6578706572696d656e74616cf50037"};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b506150066000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550610fe6806100626000396000f30060806040526004361062000043576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680639dbd28b11462000048575b600080fd5b3480156200005557600080fd5b506200007460048036036200006e9190810190620003cb565b6200008d565b60405162000084929190620005ba565b60405180910390f35b60008060008060008c8c8c620000a262000265565b620000b0939291906200063b565b604051809103906000f080158015620000cd573d6000803e3d6000fd5b5092507ffce723060091dd1452a91ae12d05541e3141b37fa27968bc557add04601f74d0836040516200010191906200059d565b60405180910390a1600090506200011b8989898962000182565b80935081925050508973ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff161415156200015e57600080fd5b600115158115151415156200017257600080fd5b5050509850989650505050505050565b6000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166322ede61e878787876040518563ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401620002039493929190620005e7565b6040805180830381600087803b1580156200021d57600080fd5b505af115801562000232573d6000803e3d6000fd5b505050506040513d601f19601f820116820180604052506200025891908101906200038a565b9150915094509492505050565b6040516107bf80620007ee83390190565b600062000284823562000761565b905092915050565b60006200029a825162000761565b905092915050565b6000620002b0825162000781565b905092915050565b6000620002c682356200078d565b905092915050565b600082601f8301121515620002e257600080fd5b8135620002f9620002f382620006bb565b6200068d565b915080825260208301602083018583830111156200031657600080fd5b6200032383828462000797565b50505092915050565b600082601f83011215156200034057600080fd5b8135620003576200035182620006e8565b6200068d565b915080825260208301602083018583830111156200037457600080fd5b6200038183828462000797565b50505092915050565b600080604083850312156200039e57600080fd5b6000620003ae85828601620002a2565b9250506020620003c1858286016200028c565b9150509250929050565b600080600080600080600080610100898b031215620003e957600080fd5b600089013567ffffffffffffffff8111156200040457600080fd5b620004128b828c016200032c565b985050602089013567ffffffffffffffff8111156200043057600080fd5b6200043e8b828c016200032c565b975050604089013567ffffffffffffffff8111156200045c57600080fd5b6200046a8b828c016200032c565b96505060606200047d8b828c0162000276565b9550506080620004908b828c01620002b8565b94505060a089013567ffffffffffffffff811115620004ae57600080fd5b620004bc8b828c01620002ce565b93505060c0620004cf8b828c01620002b8565b92505060e0620004e28b828c01620002b8565b9150509295985092959890939650565b620004fd816200072b565b82525050565b6200050e816200074b565b82525050565b6200051f8162000757565b82525050565b6000620005328262000715565b80845262000548816020860160208601620007a6565b6200055381620007dc565b602085010191505092915050565b60006200056e8262000720565b80845262000584816020860160208601620007a6565b6200058f81620007dc565b602085010191505092915050565b6000602082019050620005b46000830184620004f2565b92915050565b6000604082019050620005d1600083018562000503565b620005e06020830184620004f2565b9392505050565b6000608082019050620005fe600083018762000514565b818103602083015262000612818662000525565b905062000623604083018562000514565b62000632606083018462000514565b95945050505050565b6000606082019050818103600083015262000657818662000561565b905081810360208301526200066d818562000561565b9050818103604083015262000683818462000561565b9050949350505050565b6000604051905081810181811067ffffffffffffffff82111715620006b157600080fd5b8060405250919050565b600067ffffffffffffffff821115620006d357600080fd5b601f19601f8301169050602081019050919050565b600067ffffffffffffffff8211156200070057600080fd5b601f19601f8301169050602081019050919050565b600081519050919050565b600081519050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60008115159050919050565b6000819050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60008115159050919050565b6000819050919050565b82818337600083830152505050565b60005b83811015620007c6578082015181840152602081019050620007a9565b83811115620007d6576000848401525b50505050565b6000601f19601f83011690509190505600608060405234801561001057600080fd5b506040516107bf3803806107bf833981018060405281019080805182019291906020018051820192919060200180518201929190505050826000908051906020019061005d92919061020a565b50816001908051906020019061007492919061020a565b50806002908051906020019061008b92919061020a565b507f862dc616a336d85acf6c0a863ee6e67b03a19d22753b3455d915fddcdc414ea483838360405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b838110156100f85780820151818401526020810190506100dd565b50505050905090810190601f1680156101255780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b8381101561015e578082015181840152602081019050610143565b50505050905090810190601f16801561018b5780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b838110156101c45780820151818401526020810190506101a9565b50505050905090810190601f1680156101f15780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a15050506102af565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061024b57805160ff1916838001178555610279565b82800160010185558215610279579182015b8281111561027857825182559160200191906001019061025d565b5b509050610286919061028a565b5090565b6102ac91905b808211156102a8576000816000905550600101610290565b5090565b90565b610501806102be6000396000f30060806040526004361061004c576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680634ae70cef14610051578063995297f3146101b9575b600080fd5b34801561005d57600080fd5b50610066610249565b60405180806020018060200180602001848103845287818151815260200191508051906020019080838360005b838110156100ae578082015181840152602081019050610093565b50505050905090810190601f1680156100db5780820380516001836020036101000a031916815260200191505b50848103835286818151815260200191508051906020019080838360005b838110156101145780820151818401526020810190506100f9565b50505050905090810190601f1680156101415780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b8381101561017a57808201518184015260208101905061015f565b50505050905090810190601f1680156101a75780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390f35b3480156101c557600080fd5b506101ce610433565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561020e5780820151818401526020810190506101f3565b50505050905090810190601f16801561023b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6060806060600060016002828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156102e95780601f106102be576101008083540402835291602001916102e9565b820191906000526020600020905b8154815290600101906020018083116102cc57829003601f168201915b50505050509250818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103855780601f1061035a57610100808354040283529160200191610385565b820191906000526020600020905b81548152906001019060200180831161036857829003601f168201915b50505050509150808054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104215780601f106103f657610100808354040283529160200191610421565b820191906000526020600020905b81548152906001019060200180831161040457829003601f168201915b50505050509050925092509250909192565b606060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104cb5780601f106104a0576101008083540402835291602001916104cb565b820191906000526020600020905b8154815290600101906020018083116104ae57829003601f168201915b50505050509050905600a165627a7a723058208af0a32982e2f92488c5451e22f42138508948","faf64eaac2b64c969311c1d06b0029a265627a7a723058202bb3410f523bf171e4b0ecfcf93daf9c538b07a0bba32d9698d5fa34a34560b26c6578706572696d656e74616cf50037"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"evi\",\"type\":\"string\"},{\"name\":\"info\",\"type\":\"string\"},{\"name\":\"id\",\"type\":\"string\"},{\"name\":\"signAddr\",\"type\":\"address\"},{\"name\":\"message\",\"type\":\"bytes32\"},{\"name\":\"pubKey\",\"type\":\"bytes\"},{\"name\":\"r\",\"type\":\"bytes32\"},{\"name\":\"s\",\"type\":\"bytes32\"}],\"name\":\"insertEvidence\",\"outputs\":[{\"name\":\"\",\"type\":\"bool\"},{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"newEvidenceEvent\",\"type\":\"event\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_INSERTEVIDENCE = "insertEvidence";

    public static final Event NEWEVIDENCEEVENT_EVENT = new Event("newEvidenceEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    protected SM2EvidenceVerify(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt insertEvidence(String evi, String info, String id, String signAddr, String message, String pubKey, String r, String s) {
        final Function function = new Function(
                FUNC_INSERTEVIDENCE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(evi), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(info), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(id), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(signAddr), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(message), 
                new org.fisco.bcos.sdk.abi.datatypes.DynamicBytes(pubKey), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(r), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(s)), 
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public byte[] insertEvidence(String evi, String info, String id, String signAddr, String message, String pubKey, String r, String s, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_INSERTEVIDENCE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(evi), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(info), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(id), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(signAddr), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(message), 
                new org.fisco.bcos.sdk.abi.datatypes.DynamicBytes(pubKey), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(r), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(s)), 
                Collections.<TypeReference<?>>emptyList());
        return asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForInsertEvidence(String evi, String info, String id, String signAddr, String message, String pubKey, String r, String s) {
        final Function function = new Function(
                FUNC_INSERTEVIDENCE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(evi), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(info), 
                new org.fisco.bcos.sdk.abi.datatypes.Utf8String(id), 
                new org.fisco.bcos.sdk.abi.datatypes.Address(signAddr), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(message), 
                new org.fisco.bcos.sdk.abi.datatypes.DynamicBytes(pubKey), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(r), 
                new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(s)), 
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple8<String, String, String, String, byte[], byte[], byte[], byte[]> getInsertEvidenceInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_INSERTEVIDENCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple8<String, String, String, String, byte[], byte[], byte[], byte[]>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue(), 
                (String) results.get(3).getValue(), 
                (byte[]) results.get(4).getValue(), 
                (byte[]) results.get(5).getValue(), 
                (byte[]) results.get(6).getValue(), 
                (byte[]) results.get(7).getValue()
                );
    }

    public Tuple2<Boolean, String> getInsertEvidenceOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_INSERTEVIDENCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<Boolean, String>(

                (Boolean) results.get(0).getValue(), 
                (String) results.get(1).getValue()
                );
    }

    public List<NewEvidenceEventEventResponse> getNewEvidenceEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWEVIDENCEEVENT_EVENT, transactionReceipt);
        ArrayList<NewEvidenceEventEventResponse> responses = new ArrayList<NewEvidenceEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewEvidenceEventEventResponse typedResponse = new NewEvidenceEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.addr = (String) eventValues.getNonIndexedValues().get(0).getValue();
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

    public static SM2EvidenceVerify load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new SM2EvidenceVerify(contractAddress, client, credential);
    }

    public static SM2EvidenceVerify deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(SM2EvidenceVerify.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }

    public static class NewEvidenceEventEventResponse {
        public TransactionReceipt.Logs log;

        public String addr;
    }
}
