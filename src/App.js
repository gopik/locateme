import React, { Component } from 'react';
import { Text } from 'react-native';
import ActivityStarterModule from './ActivityStarter'
import firebase from 'firebase'

export default class App extends Component {
	state = {
		result: "None"
	}
	onIdTokenReceived(idToken) {
		console.log("Received idToken:" + idToken)
		this.setState({ result: idToken})
		credential = firebase.auth.GoogleAuthProvider.credential(idToken)
		firebase.auth().signInWithCredential(credential).then(user => this.setState( { result : `${user.email}` } ))
		.catch(err => this.setState( { result : err }))
	}
	componentWillMount() {
	  var config = {
    		apiKey: "AIzaSyBaDkqMFu2l6IbPnl3G5DivUTruU6ZkNco",
		authDomain: "locateme-95a92.firebaseapp.com",
		databaseURL: "https://locateme-95a92.firebaseio.com",
		projectId: "locateme-95a92",
		storageBucket: "locateme-95a92.appspot.com",
		messagingSenderId: "291059645618"
	  };
	  firebase.initializeApp(config);

	  ActivityStarterModule.startActivityForResult("com.locateme.LoginActivity",
			  this.onIdTokenReceived.bind(this));
	}
	render() {
		return (
				<Text>{ this.state.result }</Text>
		       );
	}
}
