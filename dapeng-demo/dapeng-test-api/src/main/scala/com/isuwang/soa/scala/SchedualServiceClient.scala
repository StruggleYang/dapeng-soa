package com.github.dapeng.soa.scala

import com.github.dapeng.core._;
        import com.github.dapeng.org.apache.thrift._;
        import java.util.ServiceLoader;
        import com.github.dapeng.soa.scala.SchedualServiceCodec._;
        import com.github.dapeng.soa.scala.service.SchedualService;

        /**
         * Autogenerated by Dapeng-Code-Generator (1.2.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated

        **/
        class SchedualServiceClient extends SchedualService {

        import java.util.function.{ Function ⇒ JFunction, Predicate ⇒ JPredicate, BiPredicate }
          implicit def toJavaFunction[A, B](f: Function1[A, B]) = new JFunction[A, B] {
          override def apply(a: A): B = f(a)
        }

          val serviceName = "com.github.dapeng.soa.service.SchedualService"
          val version = "1.0.0"
          val pool = {
            val serviceLoader = ServiceLoader.load(classOf[SoaConnectionPoolFactory])
          if (serviceLoader.iterator().hasNext) {
          val poolImpl = serviceLoader.iterator().next().getPool
          poolImpl.registerClientInfo(serviceName,version)
          poolImpl
          } else null
           }

        def getServiceMetadata: String = {
        pool.send(
          serviceName,
          version,
          "getServiceMetadata",
          new getServiceMetadata_args,
          new GetServiceMetadata_argsSerializer,
          new GetServiceMetadata_resultSerializer
        ).success
        }


        
             /**
             * 
             **/
            def test() : String = {

              val response = pool.send(
              serviceName,
              version,
              "test",
              test_args(),
              new Test_argsSerializer(),
              new Test_resultSerializer())

              response.success

            }
          
      }
      