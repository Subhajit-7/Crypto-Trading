import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label';
import { RadioGroup, RadioGroupItem } from '@/components/ui/radio-group';
import { paymentHandler } from '@/State/Wallet/Action';
import { DotFilledIcon } from '@radix-ui/react-icons';
import React, { useState } from 'react'
import { useDispatch } from 'react-redux';

const TopupForm = () => {

    const [amount, setAmount] = useState('');
    const [paymentMethod, setPaymentMethod] = useState("STRIPE");
    const dispatch = useDispatch();

    const handlePaymentMethodChange = (value) => {
        setPaymentMethod(value);
    };

    const handleChange = (e) => {
        setAmount(e.target.value);
    };

    const handleSubmit = () => {
        console.log(amount, paymentMethod);
        dispatch(paymentHandler({ jwt: localStorage.getItem("jwt"), paymentMethod, amount }));
    };

    return (
        <div className='pt-10 space-y-5'>
            <div className="">
                <h1 className='pb-1'>Enter Amount</h1>
                <Input
                    onChange={handleChange}
                    value={amount}
                    className="py-7 text-lg"
                    placeholder="$9999"
                />
            </div>
            <div className="">
                <h1 className='pb-1'>Select payment method</h1>
                <RadioGroup
                    onValueChange={(value) => handlePaymentMethodChange(value)}
                    className="flex"
                    defaultValue="STRIPE"
                >
                    <div className="flex items-center space-x-2 border p-3 px-5 rounded-md">
                        <RadioGroupItem
                            icon={DotFilledIcon}
                            className="h-9 w-9"
                            value="STRIPE"
                            id="r1"
                        />
                        <Label htmlFor="r1">
                            <div className="bg-white rounded-md px-5  py-1 w-32">
                                <img
                                    className='h-7'
                                    src="https://tse3.mm.bing.net/th?id=OIP._rsdSb1TgFPo_0cBUbZHnQHaB5&pid=Api&P=0&h=180"
                                    alt=""
                                />
                            </div>
                        </Label>
                    </div>
                    <div className="flex items-center space-x-2 border p-3 px-5 rounded-md">
                        <RadioGroupItem
                            icon={DotFilledIcon}
                            className="h-9 w-9"
                            value="RAZORPAY"
                            id="r2"
                        />
                        <Label htmlFor="r2">
                            <div className="bg-white rounded-md px-5 py-2 w-32">
                                <img src="https://tse2.mm.bing.net/th?id=OIP._PqyNynFcdKr4uiIVLs2PQHaBk&pid=Api&P=0&h=180" alt="" />
                            </div>
                        </Label>
                    </div>
                </RadioGroup>
            </div>
            <Button
                onClick={handleSubmit}
                className="w-full py-7"
            >
                Submit
            </Button>
        </div>
    )
}

export default TopupForm