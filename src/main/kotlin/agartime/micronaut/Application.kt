package agartime.micronaut

import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License

@OpenAPIDefinition(
	info = Info(
		title = "\${openapi.title}",
		version = "\${api.version}",
		description = "\${openapi.description}",
		license = License(name = "\${openapi.license.name}", url = "\${openapi.license.url}"),
		contact = Contact(
			url = "\${openapi.contact.url}",
			name = "\${openapi.contact.name}",
			email = "\${openapi.contact.email}"
		)
	)
)

object ApplicationKt {
	@JvmStatic
	fun main(args: Array<String>) {
		run(*args)
	}
}

