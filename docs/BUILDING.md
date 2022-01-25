# Building

The library relies on packages hosted on GitHub. Because of this you have to specify your GitHub username and
a personal access token (with scope `read:packages`) as environment variables.

```
GH_USER=<your_username>
GH_TOKEN=<your_token>
```

## Testing

To run integration tests you need to have docker-compose installed.

### Integration Tests

Integration tests are performed against synapse running in a container. The container is defined in
the `test-compose.yaml` file and all configuration for the synapse server is in the `synapse` directory. When performing
integration tests with gradle a gradle plugin is used to create, start, stop and remove the container. The synapse
database is not persistent.