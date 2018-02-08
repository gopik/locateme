import React, { Component } from 'react';
import { Text } from 'react-native';
import ActivityStarterModule from './ActivityStarter'

export default class App extends Component {
	componentWillMount() {
		ActivityStarterModule.startActivityForResult("com.locateme.LoginActivity")
	}
  render() {
    return (
      <Text>Hello world!</Text>
    );
  }
}
