package ru.tinkoff.task.contact;

interface ContactRepository {
  Contact get(Integer contactId);
}
