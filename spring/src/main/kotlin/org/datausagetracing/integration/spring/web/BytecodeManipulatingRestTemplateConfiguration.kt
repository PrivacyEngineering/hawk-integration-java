package org.datausagetracing.integration.spring.web

import net.bytebuddy.agent.ByteBuddyAgent
import net.bytebuddy.agent.builder.AgentBuilder
import net.bytebuddy.asm.Advice
import net.bytebuddy.matcher.ElementMatchers.named
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class BytecodeManipulatingRestTemplateConfiguration {
    @PostConstruct
    fun manipulateRestTemplate() {
        ByteBuddyAgent.install()
        AgentBuilder.Default()
            .disableClassFormatChanges()
            .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
            .type(named("org.springframework.web.client.RestTemplate"))
            .transform(AgentBuilder.Transformer.ForAdvice()
                .include(javaClass.classLoader)
                .advice(named("doExecute"), javaClass.simpleName))
//            .transform { builder, _, _, _ ->
//                builder.visit(
//                    Advice.to(BytecodeManipulatingRestTemplateConfiguration::class.java).on(
//                        ElementMatchers.isMethod()
//                    )
//                )
//                    .intercept(
//                        MethodDelegation
//                            .to(BytecodeManipulatingRestTemplateConfiguration::class.java)
//                    )
//            }
            .installOnByteBuddyAgent()
    }

    companion object {
        @Advice.OnMethodEnter
        fun enter(@Advice.This thisReference: Any,
                  @Advice.Origin origin: String,
                  @Advice.AllArguments arguments: Array<Any>) {
            println("test")
        }

//        fun intercept(
//            url: URL, method: HttpMethod, requestCallback: RequestCallback,
//            responseExtractor: ResponseExtractor<*>
//        ) {
//            println("test")
//        }
    }
}