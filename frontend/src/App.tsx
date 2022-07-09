import React from 'react';
import { Route, Routes } from 'react-router';
import './App.css';
import Header from './components/Header';
import HomePage from './page/HomePage';

function App() {
  return (
    <div>
      <Header/>
      <Routes>
        <Route path="/" element={<HomePage/>}></Route>

      </Routes>
    </div>
  );
}

export default App;
