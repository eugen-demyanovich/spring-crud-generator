# Spring CRUD Generator

![status](https://img.shields.io/badge/status-active-brightgreen)
![license](https://img.shields.io/badge/license-MIT-blue)

A small CLI tool for quickly **scaffolding CRUD layers** in a Spring Boot project.  
Generates Entity, Repository, Service, Controller—and optional Facade, DTOs & MapStruct mappers—based on your entity name and a simple YAML config.

---

## 🚀 Features

- **Entity** generation with JPA annotations  
- **Repository**: choose between Spring Data (`CrudRepository`) or a Hibernate-based stub  
- **Service** layer with standard CRUD methods  
- **Controller** with REST endpoints (`GET`, `POST`, `PUT`, `DELETE`)  
- ⚙️ **Configurable** via `.crudgen.yml`  
- 🗄️ Optional **DTO** + **MapStruct** mapper  
- 🛡️ Optional **Facade** layer for DTO ↔ Entity conversion  
- Template-based (FreeMarker `.tpl` files) — fully customizable  

---

## 🛠️ Installation & Build

Clone & build the generator itself:

```bash
git clone https://github.com/eugen-demyanovich/spring-crud-generator.git
cd spring-crud-generator
mvn clean package
````

After success, you’ll have:

```
target/spring-crud-generator.jar
```

---

## ⚙️ Configuration

Create a `.crudgen.yml` in your Spring Boot project root:

```yaml
basePackage: com.mycompany.myapp      # root Java package for generated code
dbProvider: spring-data              # spring-data or hibernate
withFacade: true                     # true → generate Facade layer
withConverter: true                  # true → generate DTO + MapStruct mapper
```

---

## 🏃‍♂️ Usage

From your project root (where `.crudgen.yml` lives):

```bash
java -jar path/to/spring-crud-generator.jar generate EntityName
```

Example:

```bash
java -jar ../spring-crud-generator/target/spring-crud-generator.jar generate Car
```

Will create under `src/main/java/com/mycompany/myapp/`:

```
model/Car.java
repository/CarRepository.java
service/CarService.java
controller/CarController.java
dto/CarDto.java           # if withConverter: true
mapper/CarMapper.java     # if withConverter: true
facade/CarFacade.java     # if withFacade: true
```

---

## 📂 Templates

All templates live in `src/main/resources/templates/` of this generator.
Feel free to tweak any of:

* `entity.tpl`
* `repository-spring-data.tpl`
* `repository-hibernate.tpl`
* `service.tpl`
* `controller.tpl`
* `dto.tpl`
* `mapper.tpl`
* `facade.tpl`

---

## 🧑‍🤝‍🧑 Contributing

1. Fork this repo
2. Create a feature branch
3. Commit your changes & push
4. Open a Pull Request — all feedback welcome!

