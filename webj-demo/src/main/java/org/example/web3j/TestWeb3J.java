package org.example.web3j;

import com.google.gson.Gson;
import io.reactivex.disposables.Disposable;
import java.math.BigInteger;
import java.net.ConnectException;
import java.util.concurrent.ExecutionException;
import org.jetbrains.annotations.NotNull;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

public class TestWeb3J {

  public static Web3j httpClient;
  public static Web3j websocketClient;

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    try {
      httpClient = getWeb3jClient(true);
      websocketClient = getWeb3jClient(false);

      // demo();
      // getETHBalance("0x09627a2DD5f54e97e4c974CdeF8ABA793d80Af45");
      // getCurrentGas();
      // getTransactionByTransactionHash("0xd2007a45a783d98e7f0c7f9100963e634608830b64ce7cd0cd4a4981cb49250e");
      // subscribeNewTransaction();
      // subscribeNewContractEvent("0x7abF52a91D3D078960BAFC9912fa1bE248ef6dcf");
      signAndSendTransaction("0x09627a2DD5f54e97e4c974CdeF8ABA793d80Af45");
    } finally {
      httpClient.shutdown();
      websocketClient.shutdown();
    }
  }

  //  签名并发送交易
  private static void signAndSendTransaction(String address)
      throws ExecutionException, InterruptedException {

    // get nonce value
    EthGetTransactionCount ethGetTransactionCount =
        httpClient
            .ethGetTransactionCount(address, DefaultBlockParameterName.PENDING)
            .sendAsync()
            .get();
    BigInteger nonce = ethGetTransactionCount.getTransactionCount();
    System.out.println(nonce);

    // build transaction
    RawTransaction etherTransaction =
        RawTransaction.createEtherTransaction(
            nonce,
            httpClient.ethGasPrice().sendAsync().get().getGasPrice(),
            DefaultGasProvider.GAS_LIMIT,
            address,
            Convert.toWei("0.0001", Convert.Unit.ETHER).toBigInteger());
    System.out.println(etherTransaction);

    // load private key
    Credentials credentials = Credentials.create("your private key");

    // use privateKey sign and send transaction
    byte[] signature = TransactionEncoder.signMessage(etherTransaction, credentials);
    String signatureHexValue = Numeric.toHexString(signature);
    EthSendTransaction ethSendTransaction =
        httpClient.ethSendRawTransaction(signatureHexValue).sendAsync().get();
    System.out.println(ethSendTransaction.getResult());
  }

  //  使用 Web3J 订阅新的合约事件
  private static void subscribeNewContractEvent(String address) {
    try {
      // 确保 WebSocket 连接成功
      if (!websocketClient.web3ClientVersion().send().hasError()) {
        EthFilter ethFilter =
            new EthFilter(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST,
                // contract address
                address);

        Disposable subscribe =
            websocketClient
                .ethLogFlowable(ethFilter)
                .subscribe(
                    event -> {
                      if (event != null) {
                        System.out.println(event);
                      } else {
                        System.err.println("Received null event.");
                      }
                    },
                    error -> {
                      System.err.println("Error in subscription: " + error.getMessage());
                      error.printStackTrace();
                    },
                    () -> System.out.println("Subscription completed"));

        // 保持程序运行一段时间以接收事件
        Thread.sleep(60000); // 等待60秒
        subscribe.dispose();
      } else {
        System.err.println("WebSocket connection failed.");
      }
    } catch (Exception e) {
      System.err.println("Error while subscribing to new transactions: " + e.getMessage());
    }
  }

  // 使用 Web3J 订阅新的交易
  private static void subscribeNewTransaction() {
    try {
      // 确保 WebSocket 连接成功
      if (!websocketClient.web3ClientVersion().send().hasError()) {
        Disposable subscribe =
            websocketClient
                .replayPastTransactionsFlowable(
                    DefaultBlockParameterName.fromString(DefaultBlockParameterName.EARLIEST.name()))
                .subscribe(
                    transaction -> System.out.println(transaction),
                    error -> System.err.println("Error: " + error.getMessage()));
      } else {
        System.err.println("WebSocket connection failed.");
      }
    } catch (Exception e) {
      System.err.println("Error while subscribing to new transactions: " + e.getMessage());
    }
  }

  // 使用 Web3J 通过交易哈希获取交易详情
  private static void getTransactionByTransactionHash(String transactionHash) {
    try {
      EthGetTransactionReceipt ethGetTransactionReceipt =
          httpClient.ethGetTransactionReceipt(transactionHash).sendAsync().get();
      TransactionReceipt transactionReceipt =
          ethGetTransactionReceipt.getTransactionReceipt().orElseThrow(RuntimeException::new);
      System.out.println(toJsonString(transactionReceipt));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  // 使用 Web3J 示例
  public static void demo() {
    Web3ClientVersion web3ClientVersion;
    try {
      web3ClientVersion = httpClient.web3ClientVersion().sendAsync().get();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
    System.out.println(web3ClientVersion.getWeb3ClientVersion());
  }

  // 使用 Web3J 获取以太坊账户余额
  public static void getETHBalance(String address) {
    Request<?, EthGetBalance> ethGetBalanceRequest =
        httpClient.ethGetBalance(
            address, DefaultBlockParameterName.fromString(DefaultBlockParameterName.LATEST.name()));
    EthGetBalance ethGetBalance = null;
    try {
      ethGetBalance = ethGetBalanceRequest.sendAsync().get();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
    System.out.println(ethGetBalance.getBalance());
  }

  //  使用 Web3J 获取当前的 Gas 价格
  public static void getCurrentGas() {
    try {
      EthGasPrice ethGasPrice = httpClient.ethGasPrice().sendAsync().get();
      System.out.printf("getCurrentGas:%s", ethGasPrice.getGasPrice());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  private static String toJsonString(Object object) {

    Gson gson = new Gson();
    return gson.toJson(object);
  }

  @NotNull
  private static Web3j getWeb3jClient(boolean isHttpClient) {
    if (isHttpClient) {
      return Web3j.build(
          // todo get your api url from https://dashboard.alchemy.com/apps
          new HttpService("https://eth-sepolia.g.alchemy.com/v2/xxxx"));
    }
    try {
      // todo get your api url from https://dashboard.alchemy.com/apps
      WebSocketService webSocketService =
          new WebSocketService("wss://eth-sepolia.g.alchemy.com/v2/xxxxx", true);
      webSocketService.connect();
      return Web3j.build(webSocketService);
    } catch (ConnectException e) {
      throw new RuntimeException(e);
    }
  }
}
