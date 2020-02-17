package ir.hillapay.pay.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import ir.hillapay.core.publicmodel.IpgCallbackModel;
import ir.hillapay.core.publicmodel.DirectdebitPayModel;
import ir.hillapay.core.publicmodel.TransactionVerifyModel;



public class HillaPaySdk {


    public static void register(Context context, String uid) {
        ir.hillapay.core.sdk.HillaPaySdk.register(context, uid);
    }

    public static void register(Context context, String uid, boolean showFirsLevel) {
        ir.hillapay.core.sdk.HillaPaySdk.register(context, uid, showFirsLevel);
    }

    public static void openTrack(Activity context, String uid) {
        ir.hillapay.core.sdk.HillaPaySdk.openTrack(context, uid);
    }

    public static void closeTrack(Activity context, String uid) {
        ir.hillapay.core.sdk.HillaPaySdk.closeTrack(context, uid);
    }

    public static void tracker(Activity context, String uid, String action, String description) {
        ir.hillapay.core.sdk.HillaPaySdk.tracker(context, uid, action, description);
    }

    /**
     * @param context
     * @param amount
     * @param phone
     * @param orderId        is unique
     * @param description
     * @param uid            is unique
     * @param additionalData
     * @param sku is product code
     * @param phoneByUser is enable get user phone
     */
    public static void payment(Activity context, String amount, String phone, String orderId,
                               String description, String uid, String additionalData, String sku,
                               boolean phoneByUser) {
        ir.hillapay.core.sdk.HillaPaySdk.payment(context, amount, phone, orderId,
                description, uid, additionalData, sku, phoneByUser);

    }

    public static void verify(Activity context, String uid, ir.hillapay.pay.sdk.IpgCallbackModel ipgCallbackModel) {
        ir.hillapay.core.publicmodel.IpgCallbackModel ipgModel = new ir.hillapay.core.publicmodel.IpgCallbackModel(
                ipgCallbackModel.getTransactionId(),
                ipgCallbackModel.getOrderId(),
                ipgCallbackModel.getStatusCode(),
                ipgCallbackModel.getStatusDescription(),
                ipgCallbackModel.getPackageName(),
                ipgCallbackModel.getReturnAmount(),
                ipgCallbackModel.getReturnRrn(),
                ipgCallbackModel.isSuccess());
        ir.hillapay.core.sdk.HillaPaySdk.verify(context, uid, ipgModel);
    }

    public static void getPaymentResult(int requestCode, int resultCode, Intent data, final ir.hillapay.pay.sdk.HillaPaySdkListener sdkListener) {
        ir.hillapay.core.sdk.HillaPaySdk.getPaymentResult(requestCode, resultCode, data, new ir.hillapay.core.sdk.HillaPaySdkListener() {
            @Override
            public void paymentResult(IpgCallbackModel ipgModel, boolean isSuccess) {
                ir.hillapay.pay.sdk.IpgCallbackModel ipgCallbackModel = new ir.hillapay.pay.sdk.IpgCallbackModel(
                        ipgModel.getTransactionId(),
                        ipgModel.getOrderId(),
                        ipgModel.getStatusCode(),
                        ipgModel.getStatusDescription(),
                        ipgModel.getPackageName(),
                        ipgModel.getReturnAmount(),
                        ipgModel.getReturnRrn(),
                        ipgModel.isSuccess());
                sdkListener.paymentResult(ipgCallbackModel, isSuccess);
            }

            @Override
            public void verifyResult(TransactionVerifyModel verifyModel, boolean isSuccess) {

                BankModel bankModel = new BankModel(
                        verifyModel.getBank().getId(),
                        verifyModel.getBank().getWithdrawalType(),
                        verifyModel.getBank().getTitle(),
                        verifyModel.getBank().getImage());

                TerminalModel terminalModel=new TerminalModel(
                        verifyModel.getTerminal().getId(),
                        verifyModel.getTerminal().getTitle());

                ir.hillapay.pay.sdk.TransactionVerifyModel transactionVerifyModel = new ir.hillapay.pay.sdk.TransactionVerifyModel(
                        verifyModel.getTransactionId(),
                        verifyModel.getOrderId(),
                        verifyModel.getCard(),
                        terminalModel,
                        bankModel,
                        verifyModel.isSuccess());
                sdkListener.verifyResult(transactionVerifyModel, isSuccess);
            }

            @Override
            public void directDebitResult(DirectdebitPayModel payModel, boolean isSuccess) {
                BankModel bankModel = new BankModel(
                        payModel.getBank().getId(),
                        payModel.getBank().getWithdrawalType(),
                        payModel.getBank().getTitle(),
                        payModel.getBank().getImage());

                TerminalModel terminalModel=new TerminalModel(
                        payModel.getTerminal().getId(),
                        payModel.getTerminal().getTitle());

                ir.hillapay.pay.sdk.DirectdebitPayModel directdebitPayModel = new ir.hillapay.pay.sdk.DirectdebitPayModel(
                        payModel.getTransactionId(),
                        payModel.getOrderId(),
                        payModel.getTransactionAmount(),
                        terminalModel,
                        bankModel,
                        payModel.isSuccess()
                );

                sdkListener.directDebitResult(directdebitPayModel, isSuccess);
            }

            @Override
            public void failed(String message, @HillaErrorType int errorType) {
                sdkListener.failed(message, errorType);
            }
        });
    }


}
