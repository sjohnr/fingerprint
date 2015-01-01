Fingerprint
===============

Fingerprint is a highly configurable ID generation library.

Fingerprint supports the distributed generation of server identifiers (SIDs) and globally unique identifiers (GUIDs) across a network, with multiple storage, clustering, and communication providers. Fingerprint does not force a specific clustering technology or approach, but provides pluggable providers to customize the service for any environment with minimal additional moving parts.

Pluggable Providers
-------------------

**MySQL**: Uses MySQL's auto-increment feature to generate and persist unique SIDs to allow for dynamic provisioning of cluster nodes. This provider can be configured to deliver IDs based on pre-defined base and step parameters, to eliminate a single point of failure.

**File**: Uses a configuration file to obtain and/or persist unique SIDs. For example, the file `/opt/fingerprint/data/myid` can contain a single unique SID for the given server, or alternatively be used to store the unique SID obtained by another provider.

**ZooKeeper**: Uses ZooKeeper as a coordination service to generate and persist unique SIDs to allow for dynamic provisioning of cluster nodes. The benefit of this provider is relatively simple configuration with no single point of failure, but requires a ZooKeeper cluster and accompanying connection parameters.

**ZeroMQ**: Uses a ZeroMQ REQ/REP pipeline to request an ID from a service. The service can be a different server configured with another provider. This provider supports the configuration of multiple remote endpoints, to eliminate a single point of failure.

Identifier Generation
---------------------

The idea here is to generate IDs on the server processing the data, instead of generating the ID during a persist operation and returning the ID. IDs generated by this library are configurable and can take numerous forms. IDs are binary, and can be 32, 64, 96, or 128 bits in length. The binary representation contains components which represent different portions of the identifier, which are inherently sortable. The core API allows IDs that were generated to be parsed as well, for display and processing purposes. Parsed IDs can take a blown-out form which contains left-padded values so that the human readable representation is also sortable.

The technique for encoding portions of the identifier is similar to numerous approaches on the web, such as [Instagram's approach](http://instagram-engineering.tumblr.com/post/10853187575/sharding-ids-at-instagram). Each range of bits contains a portion of the identifier, and the length of each bit range depends on the data stored in that portion.

For example, using something similar to Instagram's approach, you have the following 64-bit identifier.

1. Bits 1-41 (41 bits): Time, based on a configurable custom epoch (e.g. 1/1/2015)
2. Bits 42-54 (13 bits): SID, unique to each node, assigned via a provider (see above)
3. Bits 55-64 (10 bits): UID, assigned via in-memory sequence

Note that component #2 can be replaced by a Shard ID instead, using customization options. As with Instagram, this approach can generate 1024 IDs per millisecond per node for about 70 years, with a limit of 8,192 nodes. If these uniqueness or longevity guarantees are not enough (e.g. you think you can have more than 1024 records processed in a millisecond on a single server, or you need to start your epoch using the unix epoch of 1/1/1970), you can create a custom scheme with a slightly longer length. We can use a 96-bit identifier to achieve this.

1. Bits 1-44 (44 bits): Time, based on a configurable custom epoch (e.g. 1/1/2015)
2. Bits 45-60 (16 bits): SID, unique to each node, assigned via a provider (see above)
3. Bits 61-86 (26 bits): PID, assigned via a provider (see above) or in-memory sequence
4. Bits 87-96 (10 bits): UID, assigned via in-memory sequence

There are lots of other variations, but this example uses a Process ID (PID) to create a unique ID per process or stream, in addition to the UID. Using this scheme, this approach can generate 1024 IDs per millisecond per process (or thread, stream, etc.) per server for over 500 years, with a limit of nearly 700 billion streams and 65,536 nodes.