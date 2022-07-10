import React, { useContext } from 'react';
import { Route, Routes, Navigate } from 'react-router';
import './App.css';
import Header from './components/Header';
import HomePage from './page/HomePage';
import AuthPage from './page/user/AuthPage';
import ProfilePage from './page/user/ProfilePage';
import AuthContext from './store/auth-context';
import CreateAccoutPage from './page/user/CreateAccoutPage';
import Layout from './components/Layout';

function App() {

  const authContext = useContext(AuthContext);

  // authContext를 이용해 로그인 상태를 판단
  return (
    <Layout>
      <Routes>
        <Route path="/" element={<HomePage/>} />
        <Route path="/signup" 
          element={authContext.isLoggedIn ? <Navigate to="/" /> : <CreateAccoutPage />} />
        <Route path="/login/*" 
          element={authContext.isLoggedIn ? <Navigate to = "/"/> : <AuthPage />} />
        <Route path="/profile/" 
          element={!authContext.isLoggedIn ? <Navigate to="/" /> : <ProfilePage/>} />
      </Routes>
    </Layout>
  );
}

export default App;

// import {useEffect, useState} from "react";

// function App() {
//     const [message, setMessage] = useState([]);

//     useEffect(() => {
//         fetch("/hello")
//             .then((response) => {
//                 return response.json();
//             })
//             .then(function (data) {
//                 setMessage(data);
//             });
//     }, []);

//     return (
//         <div>
//             <header>
//                 <p>
//                     Edit <code>src/App.js</code> and save to reload.
//                 </p>
//                 <a
//                     className="App-link"
//                     href="https://reactjs.org"
//                     target="_blank"
//                     rel="noopener noreferrer"
//                 >
//                     Learn React
//                 </a>
//                 <ul>
//                     {message.map((text, index) => <li key={`${index}-${text}`}>{text}</li>)}
//                 </ul>
//             </header>
//         </div>
//     );
// }

// export default App;