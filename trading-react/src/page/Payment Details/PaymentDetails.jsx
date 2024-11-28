import { Button } from '@/components/ui/button'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from '@/components/ui/dialog'
import React, { useEffect } from 'react'
import PaymentDetailsForm from './PaymentDetailsForm'
import { useDispatch, useSelector } from 'react-redux'
import { getPaymentDetails } from '@/State/Withdrawal/Action'

const PaymentDetails = () => {

  const { withdrawal } = useSelector(store => store);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(getPaymentDetails({ jwt: localStorage.getItem("jwt") }));
  }, []);

  const maskAccountNumber = (accountNumber) => {
    if (accountNumber && accountNumber.length > 4) {
      return accountNumber.slice(-4).padStart(accountNumber.length, '*');
    }
    return accountNumber;
  };

  return (
    <div className='px-20'>
      <h1 className="text-3xl font-bold py-10">Payment Details</h1>
      {withdrawal.paymentDetails ? (
        <Card>
          <CardHeader>
            <CardTitle>
              {withdrawal.paymentDetails?.bankName}
            </CardTitle>
            <CardDescription>
              A/C No: {maskAccountNumber(withdrawal.paymentDetails?.accountNumber)}
            </CardDescription>
          </CardHeader>
          <CardContent>
            <div className="flex items-center">
              <p className='w-32'>A/C Holder</p>
              <p className="text-gray-400">: {withdrawal.paymentDetails?.accountHolderName}</p>
            </div>
            <div className="flex items-center">
              <p className='w-32'>IFSC</p>
              <p className="text-gray-400">: {withdrawal.paymentDetails?.ifsc}</p>
            </div>
          </CardContent>
        </Card>
      ) : (
        <Dialog>
          <DialogTrigger>
            <Button
              className="py-6"
            >
              Add payment details
            </Button>
          </DialogTrigger>
          <DialogContent>
            <DialogHeader>
              <DialogTitle>Payment Details</DialogTitle>
            </DialogHeader>
            <PaymentDetailsForm />
          </DialogContent>
        </Dialog>
      )}
    </div>
  )
}

export default PaymentDetails