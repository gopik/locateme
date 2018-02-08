import React, { Component } from 'react';
import { Text } from 'react-native';
import ActivityStarterModule from './ActivityStarter'

export default class App extends Component {
	state = {
		result: "None"
	}
	componentWillMount() {
		ActivityStarterModule.startActivityForResult("com.locateme.LoginActivity",
			       	result => this.setState({result}))
	}
	render() {
		return (
				<Text>{ this.state.result }</Text>
		       );
	}
}
