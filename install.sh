#!/bin/bash

azure servicefabric application package copy HelloWorldApplication fabric:ImageStore
azure servicefabric application type register HelloWorldApplication
azure servicefabric application create fabric:/HelloWorldApplication  HelloWorldApplicationType 1.0.0

