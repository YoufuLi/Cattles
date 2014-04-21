# Cattles

------

Scientific workflow management systems (SWFMS) have been proven essential to scientific computing and services computing as they provide functionalities such as workflow specification, process coordination, job scheduling and execution, provenance tracking, and fault tolerance. As an emerging computing paradigm, Cloud computing is gaining tremendous momentum in both academia and industry: not long after Amazon opened its Elastic Computing Cloud (EC2) to the public, Google, IBM, and Microsoft all released their Cloud platforms one after another. Meanwhile, several open source Cloud platforms, such as Hadoop, OpenNebula, Eucalyptus, Nimbus, and OpenStack, become available with fast growth of their own communities. Scientific workflow systems have been formerly applied over a number of execution environments such as workstations, clusters/grids, and supercomputers, where the new Cloud computing paradigm with unprecedented size of datacenter-level resource pool and on-demand resource provisioning can offer much more to such systems, enabling scientific workflow solutions capable of addressing peta-scale scientific problems.

We propose a structured service framework (illustrated in following Figure) that covers all the major aspects involved in the migration and integration of SWFMS into the Cloud, including:

> * Client-side Workflow Specification
> * Service-based Workflow Submission and Management
> * Task Scheduling and Execution
> * Cloud Resource Management and Provisioning
![service framework](http://www.cloud-uestc.cn/projects/serviceframework/images/Service%20Framework-small.png)
------

## Cloud Resource Manager

Aimed at cloud resource management and provisioning, Cloud Resource Manager is a key module in the Service framework, which is responsible for supporting various underlying Cloud Computing Infrastructures. Introducing the reference architecture for CRM can contribute to the standardization of CRM implementation in the proposed service framework and achieve the reusability of resource management modules. Application developers do not need to implement different resource management modules on different IaaS Cloud platforms.

## Contributing to Cattles
Developers interested in our work can fork the project and we can work together to make the project more valuable. Currently, we are mainly focused on the integration of other Clouds and the dynamic resource scheduling mechanism, which can adjust the amount of computing/storage resource dynamically according to current workload.

