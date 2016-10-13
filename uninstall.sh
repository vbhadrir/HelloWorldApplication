#!/bin/bash

azure servicefabric application delete fabric:/HelloWorldApplication
azure servicefabric application type unregister HelloWorldApplicationType 1.0.0
