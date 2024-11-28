import { Button } from '@/components/ui/button'
import React, { useEffect, useRef, useState } from 'react'
import AssetTable from './AssetTable';
import StockChart from './StockChart';
import { Avatar, AvatarImage } from '@/components/ui/avatar';
import { Cross1Icon, DotIcon } from '@radix-ui/react-icons';
import { MessageCircle } from 'lucide-react';
import { Input } from '@/components/ui/input';
import { getCoinList, getTop50CoinList } from '@/State/Coin/Action';
import { useDispatch, useSelector } from 'react-redux';
import {
    Pagination,
    PaginationContent,
    PaginationEllipsis,
    PaginationItem,
    PaginationLink,
    PaginationNext,
    PaginationPrevious,
} from "@/components/ui/pagination"
import api from '@/config/api';

const Home = () => {

    const [category, setCategory] = useState("all");
    const [inputValue, setInputValue] = useState("");
    const [isBotRealease, setIsBotRealease] = useState(false);
    const [messages, setMessages] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const messagesEndRef = useRef(null);

    const { auth, coin } = useSelector(store => store);
    const dispatch = useDispatch();

    const handleBotRealease = () => {
        setIsBotRealease(!isBotRealease);
        setMessages([]);
    };

    const handleCategory = (value) => {
        setCategory(value);
    };

    const handleChange = (e) => {
        setInputValue(e.target.value);
    };

    const handleKeyPress = async (event) => {
        if (event.key === "Enter" && inputValue.trim() !== "") {
            const userMessage = inputValue.trim();
            setMessages(prevMessages => [...prevMessages, { sender: 'user', text: userMessage }]);
            setInputValue("");
            setIsLoading(true);

            try {
                // Make API request to backend
                const response = await api.post('/ai/chat', { prompt: userMessage });
                const botResponse = response.data.message;

                // Update messages with bot response
                setMessages(prevMessages => [...prevMessages, { sender: 'bot', text: botResponse }]);
            } catch (error) {
                console.error("Error fetching chatbot response:", error);
                setMessages(prevMessages => [...prevMessages, { sender: 'bot', text: "Sorry, I couldn't process your request." }]);
            } finally {
                setIsLoading(false);
            }
        }
    };

    // Function to auto-scroll to the latest message
    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    };

    useEffect(() => {
        scrollToBottom();
    }, [messages]);

    useEffect(() => {

        if (category === "all") {
            dispatch(getCoinList(1));
        } else if (category === "top50") {
            dispatch(getTop50CoinList());
        }

    }, [category, dispatch]);

    return (
        <div className='relative'>
            <div className="lg:flex">
                <div className="lg:w-[50%] lg:border-r">
                    <div className="p-3 flex items-center gap-4">
                        <Button
                            onClick={() => handleCategory("all")}
                            variant={category == "all" ? "default" : "outline"}
                            className="rounded-full"
                        >
                            All
                        </Button>
                        <Button
                            onClick={() => handleCategory("top50")}
                            variant={category == "top50" ? "default" : "outline"}
                            className="rounded-full"
                        >
                            Top 50
                        </Button>
                        <Button
                            onClick={() => handleCategory("topGainers")}
                            variant={category == "topGainers" ? "default" : "outline"}
                            className="rounded-full"
                        >
                            Top Gainers
                        </Button>
                        <Button
                            onClick={() => handleCategory("topLosers")}
                            variant={category == "topLosers" ? "default" : "outline"}
                            className="rounded-full"
                        >
                            Top Losers
                        </Button>
                    </div>
                    <AssetTable
                        coin={category === "all" ? coin.coinList :
                            category === "top50" ? coin.top50 :
                                category === "topGainers" ? coin.topGainers :
                                    coin.topLosers}
                        category={category}
                    />
                    <div>
                        <Pagination>
                            <PaginationContent>
                                <PaginationItem>
                                    <PaginationPrevious href="#" />
                                </PaginationItem>
                                <PaginationItem>
                                    <PaginationLink href="#">1</PaginationLink>
                                </PaginationItem>
                                <PaginationItem>
                                    <PaginationEllipsis />
                                </PaginationItem>
                                <PaginationItem>
                                    <PaginationNext href="#" />
                                </PaginationItem>
                            </PaginationContent>
                        </Pagination>

                    </div>
                </div>
                <div className="hidden lg:block lg:w-[50%] p-5">
                    <StockChart coinId={coin.coinList[0]?.id} />
                    <div className="flex gap-5 items-center">
                        <div>
                            <Avatar>
                                <AvatarImage src={coin.coinList[0]?.image} />
                            </Avatar>
                        </div>
                        <div>
                            <div className="flex items-center gap-2">
                                <p>{coin.coinList[0]?.symbol.toUpperCase()}</p>
                                <DotIcon className="text-gray-400" />
                                <p className="text-gray-400">{coin.coinList[0]?.name}</p>
                            </div>
                            <div className='flex items-end gap-2'>
                                <p className='text-xl font-bold'>{coin.coinList[0]?.current_price}</p>
                                <p className='text-red-600'>
                                    <span>{coin.coinList[0]?.market_cap_change_24h}</span>
                                    <span>({coin.coinList[0]?.market_cap_change_percentage_24h}%)</span>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            {/* Chatbot Section */}
            <section className='absolute bottom-5 right-5 z-40 flex flex-col justify-end items-end gap-2'>
                {isBotRealease && (
                    <div className="rounded-md w-[20rem] md:w-[25rem] lg:w-[25rem] h-[70vh] bg-slate-900 flex flex-col">
                        {/* Chatbot Header */}
                        <div className='flex justify-between items-center border-b px-6 h-[12%]'>
                            <p className="text-white font-semibold">Chat Bot</p>
                            <Button
                                onClick={handleBotRealease}
                                variant="ghost"
                                size="icon"
                            >
                                <Cross1Icon className="text-white" />
                            </Button>
                        </div>

                        {/* Chatbot Messages */}
                        <div className="h-[76%] flex flex-col overflow-y-auto gap-5 px-5 py-2 scroll-container">
                            <div className="self-start pb-5 w-auto">
                                <div className="justify-end self-end px-5 py-2 rounded-md bg-slate-800 w-auto">
                                    <p>hi, {auth.user?.fullName}</p>
                                    <p>you can ask crypto related any question</p>
                                    <p>like, price, market cap extra...</p>
                                </div>
                            </div>
                            {messages.map((msg, index) => (
                                <div
                                    key={index}
                                    className={`flex ${msg.sender === 'user' ? 'justify-end' : 'justify-start'}`}
                                >
                                    <div className={`px-4 py-2 rounded-md ${msg.sender === 'user' ? 'bg-blue-600 text-white' : 'bg-slate-800 text-white'}`}>
                                        <p>{msg.text}</p>
                                    </div>
                                </div>
                            ))}
                            {isLoading && (
                                <div className="flex justify-start">
                                    <div className="px-4 py-2 rounded-md bg-slate-800 text-white">
                                        <p>Typing...</p>
                                    </div>
                                </div>
                            )}
                            <div ref={messagesEndRef} />
                        </div>

                        {/* Chatbot Input */}
                        <div className="h-[12%] border-t p-2">
                            <Input
                                className="w-full h-full border-none outline-none bg-slate-800 text-white"
                                placeholder="Write your message..."
                                onChange={handleChange}
                                value={inputValue}
                                onKeyPress={handleKeyPress}
                            />
                        </div>
                    </div>
                )}

                {/* Chatbot Toggle Button */}
                <div className="relative w-[10rem] cursor-pointer group">
                    <Button
                        onClick={handleBotRealease}
                        className='w-full h-[3rem] gap-2 items-center bg-blue-500 hover:bg-blue-600'
                    >
                        <MessageCircle
                            size={30}
                            className='fill-white -rotate-90 stroke-none group-hover:fill-gray-800'
                        />
                        <span className='text-lg text-white'>Chat Bot</span>
                    </Button>
                </div>
            </section>
        </div>
    )
}

export default Home