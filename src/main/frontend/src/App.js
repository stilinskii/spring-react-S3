import React, { useState, useEffect } from 'react';
import './App.css';
import axios from 'axios';

const UserProfiles = () => {
  const fetchUserProfiles = () => {
    fetch('http://localhost:8081/api/v1/user-profile')
      .then((res) => res.json())
      .then((json) => {
        console.log(json);
      });
    // axios.get('http://localhost:8081/api/v1/user-profile').then((res) => {
    //   console.log(res);
    // });
  };

  useEffect(() => {
    fetchUserProfiles();
  }, []);

  return <h1>hello</h1>;
};

function App() {
  return (
    <div className='App'>
      <UserProfiles />
    </div>
  );
}

export default App;
