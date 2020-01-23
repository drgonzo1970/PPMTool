import React, { Component } from "react";
import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";

class App extends Component {
  render() {
    return (
      <div className="App">
        <Header />
        <Dashboard />
      </div>
    );
  }
}

//function App() {
//  return (
//    <div className="App">
//      <Dashboard />
//    </div>
//  );
//}

export default App;
